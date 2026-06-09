#!/bin/sh
# エラーが発生した時点スクリプト終了
set -e

# 出力先ディレクトリ準備
OUTPUT_DIR="${NGINX_ENVSUBST_OUTPUT_DIR:-/etc/nginx}"
mkdir -p "$OUTPUT_DIR"

# 環境変数の自動抽出と置換
for template in /etc/nginx/templates/*.template; do
    # ファイルが存在しない場合はスキップ
    [ -e "$template" ] || continue

    output="$OUTPUT_DIR/$(basename "${template%.template}")"

    AUTO_VARS=$(grep -oE '\$\{[A-Z0-9_]+\}' "$template" | sort -u | tr '\n' ',')
    
    if [ -n "$AUTO_VARS" ]; then
        envsubst "$AUTO_VARS" < "$template" > "$output"
    else
        cp "$template" "$output"
    fi
done

# Nginx起動
exec nginx -g 'daemon off;'
