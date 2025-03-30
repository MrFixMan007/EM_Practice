# 1. Задача по теме котлин
Была сделана на Андроид-проекте
# 2. Задача по теме корутин
* [Дока, на которую ориентировался](https://reactivex.io/documentation/operators/sample.html)
* [Статья, которая помогла](https://krossovochkin.com/posts/2020_03_02_from_rxjava_to_kotlin_flow_throttling/)\
Работу методов можно проверить при запуске методов в main\
Тестовые данные:\
![image](https://github.com/user-attachments/assets/7230ab26-5ba8-4575-9044-881ea33c98fa)

## 1. throttleFirst
![image](https://github.com/user-attachments/assets/c7486ddc-532b-49e6-9af8-2bc7407b1db0)
![image](https://github.com/user-attachments/assets/e2f8cd46-6762-4b5e-9e56-15db9effaa57)\
При вызове метода на тестовых данных должно выести: 1, 4, 5, 6, 8

## 2. throttleLast
![image](https://github.com/user-attachments/assets/93179792-9a9a-4be9-8aa9-ea192229361e)
![image](https://github.com/user-attachments/assets/6ca3dce5-a40e-47e8-bd49-67cd8ed53b5d)\
При вызове метода на тестовых данных должно выести: 3, 4, 5, 7

## 3. throttleLatest
![image](https://github.com/user-attachments/assets/4e311b53-a1fc-44d9-8fb5-7db6daf7d531)\
При вызове метода на тестовых данных должно выести:  1, 3, 4, 5, 6, 7
