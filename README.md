<img src="https://github.com/helpdeveloper/githubers/blob/master/app/src/main/ic_launcher-web.png" width="128">

# Githubers

 Githubers é um aplicativo que utiliza a [API do GitHub](https://developer.github.com/v3/) para realizar consultas e favoritar usuários ou repositórios.
   
 A idéia desse projeto é documentar e desenvolver de maneira simples quase todos os novos recursos do JetPack
e outras dependências essenciais no desenvolvimento de uma aplicação bem arquitetada seguindo os passos do próprio
guia de arquitetura do Google.
 
 Funcionalidades: 
   
 - Lista de usuários favoritos;
 - Lista de usuários genérica retornada pela API
 - Detalhes do usuário;
 - Lista de repositórios do usuário;
 - Detalhes do repositório;
 - Lista de repositórios favoritos;
 - Busca de usuários;
 - Busca de repositórios;
 
 Projeto em desenvolvimento...
 
## Dependencies / Libraries:

   - [JetPack](https://developer.android.com/jetpack/docs/guide)
   - [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
   - [Data Binding](https://developer.android.com/topic/libraries/data-binding/)
   - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
   - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
   - [Worker Manager](https://developer.android.com/topic/libraries/architecture/workmanager/)
   - [Paging](https://developer.android.com/topic/libraries/architecture/paging/)
   - [Room](https://developer.android.com/topic/libraries/architecture/room)
   
   - [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
   - [Dagger](https://google.github.io/dagger/)
   - [Glide](https://bumptech.github.io/glide/)
   - [Retrofit](https://square.github.io/retrofit/)
    
   Veja as dependências utilizadas: [build.gradle](/app/build.gradle)

## Tutoriais e guias

### Android guides / architecture / Projects (github)

 - [Android guide](https://developer.android.com/guide/)
 - [GitHub - android-architecture-components](https://github.com/googlesamples/android-architecture-components)
 - [GitHub - android-sunflower](https://github.com/googlesamples/android-sunflower)
 - [CodeLabs - android-paging](https://codelabs.developers.google.com/codelabs/android-paging)

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
     - https://gist.github.com/gbzarelli/5012ecd63268e26d8ef63c36ed2ee643

## License

[Apache-2.0](https://choosealicense.com/licenses/apache-2.0/)
