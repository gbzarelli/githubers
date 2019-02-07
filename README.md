<img src="https://github.com/helpdeveloper/githubers/blob/master/app/src/main/ic_launcher-web.png" width="128">

# Githubers

   Githubers é um aplicativo que utiliza a API do GitHub para realizar consultas e favoritar usuários ou repositórios.
   
   A idéia desse projeto é documentar e desenvolver de maneira simples quase todos os novos recursos do JetPack
 e outras dependências essenciais no desenvolvimento de uma aplicação bem arquitetada seguindo os passos do próprio
 guia de arquitetura do Google.
 
## Tecnologias / Recursos:

    - JetPack, Navigation, Room, Glide, Coroutines, Retrofit, Data Binding, LiveData, ViewModel, Dagger2, 
    Worker, Gson, etc.
    
    Veja as dependências utilizadas: [build.gradle](./app/build.gradle)

## Funcionalidades:

    - Lista de usuários favoritos;
    - Lista de usuários genérica retornada pela API;
    - Detalhes do usuário;
    - Lista de repositórios do usuário;
    - Detalhes do repositório;
    - Lista de repositórios favoritos;
    - Busca de usuários;
    - Busca de repositórios;

## Links:

### Android guides / architecture
    - https://developer.android.com/jetpack/docs/guide
    - https://developer.android.com/guide/
    - https://github.com/googlesamples/android-architecture-components
    - https://github.com/googlesamples/android-sunflower

### Docs
    - https://developer.github.com/v3/

### Coroutines
    - https://proandroiddev.com/kotlin-coroutines-patterns-anti-patterns-f9d12984c68e
    - https://proandroiddev.com/coroutines-for-android-6f9b9f966056
    
    - Coroutine Adapter para Retrofit2: 
        - https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter
        - https://android.jlelse.eu/kotlin-coroutines-and-retrofit-e0702d0b8e8f
    
### Room - Object relation map (ORM)
    - https://medium.com/androiddevelopers/7-pro-tips-for-room-fbadea4bfbd1

### Dagger - Dependency injector (DI)
    - https://github.com/googlesamples/android-architecture-components/tree/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/di
    - Dagger com Worker:
        - https://stackoverflow.com/questions/52434165/dagger2-unable-to-inject-dependencies-in-workmanager

## License
[Apache-2.0](https://choosealicense.com/licenses/apache-2.0/)