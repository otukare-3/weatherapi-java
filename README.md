# weatherapi

<https://roadmap.sh/projects/weather-api-wrapper-service>

## 前提条件

- 環境変数`WEATEHR_API_KEY`の設定が必須
- redisサーバーをDockerで起動する
  ```powershell
  docker run --name redis -p 6379:6379 -d redis
  ```

## 開発時のメモ

- APIの確認にPostmanを利用すると便利
    - `localhost`のAPIの確認はデスクトップエージェントのインストールが必要
