# ランタイムステージ
FROM nexus.sbisec.int:8444/sec-cb/amazon/amazonlinux-2023-base:20260415-1

# メタデータラベル設定
LABEL org.label-schema.description="IFA診断用サイドカーイメージ-JDK/Arthas/cfr"

RUN dnf -y update && \
    dnf -y install bind-utils createrepo dnf-utils findutils hostname iproute iputils less net-tools nmap-ncat tar telnet unzip vim wget zip && \
    dnf -y clean all && rm -rf /var/cache /tmp/* /var/tmp/*

RUN dnf install -y java-25-amazon-corretto-devel tcpdump && dnf clean all && rm -rf /var/cache /tmp/* /var/tmp/*

WORKDIR /opt/tools

RUN wget -e use_proxy=yes -e http_proxy=http://prod-proxy.jp.sbibits.com:8080 \
    https://github.com/leibnitz27/cfr/releases/download/0.152/cfr-0.152.jar -O cfr.jar && \
    printf '#!/bin/bash\njava -jar /opt/tools/cfr.jar "$@"\n' > /usr/local/bin/cfr && \
    chmod +x /usr/local/bin/cfr

RUN wget -e use_proxy=yes -e http_proxy=http://prod-proxy.jp.sbibits.com:8080 \
    https://github.com/alibaba/arthas/releases/download/arthas-all-4.1.8/arthas-bin.zip -O arthas-bin.zip && \
    unzip arthas-bin.zip -d /opt/tools && \
    rm arthas-bin.zip && \
    ln -s /opt/tools/as.sh /usr/local/bin/arthas && \
    chmod +x /usr/local/bin/arthas

ENV JAVA_HOME=/usr/lib/jvm/java-25-amazon-corretto

ENV PATH=$PATH:$JAVA_HOME/bin

WORKDIR /root

CMD ["tail", "-f", "/dev/null"]
