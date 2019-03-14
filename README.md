<img src="https://github.com/helpdeveloper/githubers/blob/master/app/src/main/ic_launcher-web.png" width="128">

# Githubers
 ### Project under development (Projeto em desenvolvimento) ...
 
[pt]
 Githubers é um aplicativo que utiliza a [API do GitHub](https://developer.github.com/v3/) para realizar 
 consultas e favoritar usuários ou repositórios.
   
 A idéia desse projeto é documentar e desenvolver de maneira simples quase todos os novos recursos do JetPack
e outras dependências essenciais no desenvolvimento de uma aplicação bem arquitetada seguindo os passos do 
próprio
guia de arquitetura do Google.

[en]
 Githubers is an application that uses the [GitHub API](https://developer.github.com/v3/) to perform queries 
 and favor users or repositories. 
 
 The idea behind this project is to document and develop in a simple way almost all new JetPack features and 
 other essential dependencies in developing a well-designed application following the steps of Google's own 
 architectural guide. 
 
 ------
 
  Technologies / Resources: 
  
   JetPack, Navigation, Room, Glide, Coroutines, Retrofit, Data Binding, LiveData, ViewModel, Dagger2, Worker, 
   Gson, ContentProvider, SearchableView, Category Browsable etc.
 
 Functionalities: 
   
 - List of favorite users;
 - Generic list of users returned by the API
 - List of user repositories;
 - List of user repositories;
 - Repository details;
 - List of favorite repositories;
 - Search of users;
 - Search of repositories;
 - Open in-app external links from github.com
 
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
    
   See the dependencies used: [build.gradle](/app/build.gradle)

## Tutorials and guides

### Android guides / architecture / Projects (github)

 - [Android guide](https://developer.android.com/guide/)
 - [GitHub - android-architecture-components](https://github.com/googlesamples/android-architecture-components)
 - [GitHub - android-sunflower](https://github.com/googlesamples/android-sunflower)
 - [CodeLabs - android-paging](https://codelabs.developers.google.com/codelabs/android-paging)
 - [Search Interface](https://developer.android.com/guide/topics/search/search-dialog)
 
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
