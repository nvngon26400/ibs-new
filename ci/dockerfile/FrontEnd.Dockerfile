# ビルドステージ
FROM nexus.sbisec.int:8444/sec-cb/java-21-builder:20250730-1 AS builder

# 作業ディレクトリ設定
WORKDIR /app

# package.jsonとpackage-lock.jsonコピー
COPY ifa-portal-frontend/package*.json ./

# 依存関係インストール
RUN npm -g config set registry=https://nexus.jp.sbibits.com/repository/npmjs-central/
RUN npm config set strict-ssl false
RUN npm config get registry

RUN npm ci --legacy-peer-deps --verbose

# ソースコードコピー
COPY ifa-portal-frontend/ .

# Vue.jsアプリケーション構築
RUN npm run build:prod

# ランタイムステージ
FROM nexus.sbisec.int:8444/sec-cb/amazon/amazonlinux-2023-base:20260415-1 AS runtime

# パラメータを宣言する
ARG IFA_USER
ARG IFA_GROUP
ARG IFA_UID
ARG IFA_GID

# ユーザーとユーザーグループ作成、タイムゾーン設定、ディレクトリ作成、権限設定を1つのRUNで実行
RUN dnf update -y && \
    dnf install -y nginx tzdata gettext shadow-utils && \
    dnf clean all && \
    rm -rf /var/cache/dnf && \
    ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    echo "Asia/Tokyo" > /etc/timezone && \
    groupadd -g ${IFA_GID} ${IFA_GROUP} && \
    useradd -u ${IFA_UID} -g ${IFA_GROUP} -m ${IFA_USER} && \
    mkdir -p /var/app/data/nginx/faq \
             /var/app/data/nginx/releaseNote \
             /usr/share/nginx/html/ui \
             /etc/nginx/templates

# npmコンパイル成果物をuiディレクトリにコピー
COPY --from=builder --chown=${IFA_UID}:${IFA_GID} /app/dist/ /usr/share/nginx/html/ui/

# Nginx設定コピー
COPY ci/nginx/nginx.conf.template /etc/nginx/templates/nginx.conf.template

# エントリーポイントコピー
COPY --chown=${IFA_UID}:${IFA_GID} --chmod=755 ci/entrypoint/frontend-entrypoint.sh /app/entrypoint.sh

ENV NGINX_ENVSUBST_OUTPUT_DIR=/etc/nginx

# ポート公開
EXPOSE ${NGINX_PORT:-80}

# Nginx起動
ENTRYPOINT ["/app/entrypoint.sh"]
