# ビルドステージ
FROM nexus.sbisec.int:8444/sec-cb/java-21-builder:20250730-1 AS builder

# 作業ディレクトリ設定
WORKDIR /app

# pomファイルコピー
COPY ifa-portal-backend/pom.xml ./
COPY ifa-portal-backend/ifa-portal-ap/pom.xml ./ifa-portal-ap/
COPY ifa-portal-backend/ifa-portal-gw/pom.xml ./ifa-portal-gw/
COPY ifa-portal-backend/ifa-portal-common/pom.xml ./ifa-portal-common/
COPY mvn_settings.xml ./settings.xml

# 依存関係ダウンロード
RUN mvn -s ./settings.xml dependency:go-offline -B -pl ifa-portal-ap -am

# ソースコードコピー
COPY ifa-portal-backend/ifa-portal-common/src ./ifa-portal-common/src/
COPY ifa-portal-backend/ifa-portal-ap/src ./ifa-portal-ap/src/

# プロジェクトビルド
RUN mvn -s ./settings.xml clean package -DskipTests -Pprod -B -pl ifa-portal-ap -am

# ランタイムステージ
FROM nexus.sbisec.int:8444/sec-cb/amazon/amazonlinux-2023-java-25:20260409-1 AS runtime

# パラメータ宣言
ARG IFA_USER
ARG IFA_GROUP
ARG IFA_UID
ARG IFA_GID

# ユーザー作成
RUN dnf install -y shadow-utils && \
    groupadd -g ${IFA_GID} ${IFA_GROUP} && \
    useradd -u ${IFA_UID} -g ${IFA_GROUP} -m ${IFA_USER} && \
    mkdir -p /app && chown -R ${IFA_UID}:${IFA_GID} /app && \
    dnf clean all

# 作業ディレクトリ設定
WORKDIR /app

# ビルド成果物コピー
COPY --chown=${IFA_UID}:${IFA_GID} --from=builder /app/ifa-portal-ap/target/ifa-portal-ap-boot.jar ./helios-backend-ap.jar

# エントリーポイントコピー
COPY --chown=${IFA_UID}:${IFA_GID} --chmod=755 ci/entrypoint/ap-entrypoint.sh /app/entrypoint.sh

# ifauserユーザー切り替え
USER ${IFA_USER}

# ポート公開
EXPOSE ${AP_SERVER_PORT:-8081}

# アプリケーション起動
ENTRYPOINT ["/app/entrypoint.sh"]
