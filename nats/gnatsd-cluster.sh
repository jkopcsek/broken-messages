gnatsd -p 4222 -cluster nats://localhost:4248 -D
gnatsd -p 5222 -cluster nats://localhost:5248 -routes nats://localhost:4248 -D
gnatsd -p 6222 -cluster nats://localhost:6248 -routes nats://localhost:4248 -D
