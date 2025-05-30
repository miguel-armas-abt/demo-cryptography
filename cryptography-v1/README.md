
[← Regresar](../README.md) <br>

---
## 📋 Core library
[🌐 Documentación](https://github.com/miguel-armas-abt/backend-core-library) <br>
[🏷️ Versión](./src/main/java/com/demo/poc/commons/core/package-info.java) <br>

---

## ▶️ Despliegue local

1. Generar el compilado
```sh
mvn clean install
```

2. Configurar las [variables de entorno](./variables.env) en el IDE.

2. Ejecutar aplicación


---

## ▶️ Despliegue con Docker

⚙️ Crear imagen
```shell
docker build -t miguelarmasabt/cryptography:v1.0.1 -f ./Dockerfile .
```

⚙️ Crear red
```shell
docker network create --driver bridge common-network
```

⚙️ Ejecutar contenedor
```shell
docker run --rm -p 8080:8080 --env-file ./variables.env --name cryptography-v1 --network common-network miguelarmasabt/cryptography:v1.0.1
```

---

## ▶️ Despliegue con Kubernetes

⚙️ Encender Minikube
```shell
docker context use default
minikube start
```

⚙️ Crear imagen
```shell
eval $(minikube docker-env --shell bash)
docker build -t miguelarmasabt/cryptography:v1.0.1 -f ./Dockerfile .
```

⚙️ Crear namespace y aplicar manifiestos
```shell
kubectl create namespace poc
kubectl apply -f ./k8s.yaml -n poc
```

⚙️ Eliminar orquestación
```shell
kubectl delete -f ./k8s.yaml -n poc
```

⚙️ Port-forward
```shell
kubectl port-forward <pod-id> 8080:8080 -n poc
```
