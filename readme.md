## 목차
- [1. MVVM](#1-mvvm)
- [2. MVVM ViewModel vs AAC ViewModel](#2-mvvm-viewmodel-vs-aac-viewmodel)
- [3. ViewModel의 사용](#3-viewmodel의-사용)
- [4. LiveData](#4-livedata)
- [5. DataBinding](#5-databinding)
- [6. Coroutine](#6-coroutine)
- [7. Navigation](#7-navigation)

---
### 1. MVVM
<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/161014604-77f7115a-6998-4f0b-8c24-8305a37bad1c.png">

<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/160877526-8d3730f7-24a4-444e-a03c-4c0df3bd4456.png">

🔗 [공식문서](https://developer.android.com/topic/libraries/architecture?hl=ko)

🔗 [MVC vs MVVM](https://velog.io/@haero_kim/Android-%EA%B9%94%EC%8C%88%ED%95%98%EA%B2%8C-MVVM-%ED%8C%A8%ED%84%B4%EA%B3%BC-AAC-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0)

- View는 ViewModel의 참조를 가지고 있지만, ViewModel은 View의 참조를 가지고 있지X
- ViewModel은 Model의 참조를 가지고 있지만, Model은 ViewModel의 참조를 가지고 있지X

> 그럼 ViewModel은 어떻게 View의 함수를 호출할 수 있나?
- View가 ViewModel을 Binding 하고 있으면 됨
- ViewModel은 단순히 값을 바꾸기만 하고, View는 그 값이 바뀌는걸 관찰하는 역할


|View|ViewModel|Model|
|------|---|---|
|1. Activity / Fragment가 View의 역할을 함 |1. View가 요청한 데이터를 Model로 요청함|1. ViewModel이 요청한 데이터를 반환함|
|2. 사용자의 Action을 받음(버튼 클릭, 텍스트 입력 등)|2. Model로부터 요청한 데이터를 받음|Room, Realm과 같은 DB 사용이나 Retrofit을 통한 백엔드 API호출(네트워킹 작업)이 보편적|
|3. ViewModel의 데이터를 관찰하여 UI 갱신|||

> MVVM의 장점!
- MVVM은 단방향 의존만 가지게 되는 것이 장점
- View 가 ViewModel 의 Data 를 관찰하고 있으므로 UI 업데이트가 간편
- ViewModel 이 데이터를 홀드하고 있으므로 Memory Leak 발생 가능성 배제
- (View 가 직접 Model 에 접근하지 않아 Activity / Fragment 라이프 사이클에 의존하지 않기 때문)
- 기능별 모듈화가 잘 되어 유지 보수에 용이 (e.g. ViewModel 재사용 및 DB 교체 등의 작업이 편리함)


---
### 2. MVVM ViewModel vs AAC ViewModel
- MVMM 아키텍처 패턴의 ViewModel과 AAC의 ViewModel의 차이

> **MVVM** 패턴 이야기 하면서 구글 이야기를 한다면 자리를 박차고 나가라. - 강사룡님

> **MVVM**은 **AAC**의 **ViewModel**과 연관성이 없다. **AAC ViewModel**을 전제로 **MVVM**을 설명하려고 한다면 단언컨데 우리 회사 1차 면접도 통과하지 못할 것이다 - 정승욱님

- [[Android]MVVM ViewModel과 AAC ViewModel의 차이](https://medium.com/kenneth-android/android-mvvm-viewmodel%EA%B3%BC-aac-viewmodel%EC%9D%98-%EC%B0%A8%EC%9D%B4-8c0d54922e07)

- [MVVM ViewModel vs AAC ViewModel](https://www.notion.so/MVVM-ViewModel-vs-AAC-ViewModel-ef453da743a54f3b9efa3d4c6b9e38aa)

<img width="600" alt="image" src="https://user-images.githubusercontent.com/85485290/161016510-4ea12552-2631-4099-8cc2-37ec8bf7f46f.png">

- MVVM ViewModel
> View와 Model 사이에서 데이터를 관리해주고 바인딩 해주는 역할(매개체 역할)

> 비즈니스 로직과 프레젠테이션 로직을 UI로부터 분리하는 것을 목표로 함

- AAC ViewModel
> AAC의 ViewModel은 화면 전환과 같은 환경에서 데이터를 보관하고, 라이프사이클을 알고 있어서 Activity나 Fragment의 Destory시 onClear함수를 통한 데이터 해제의 역할을 하는 Adnroid JetPack 라이브러리


> 구글에서 ViewModel의 목적을 데이터 관리 및 바인딩이라고 만든 것이 아님!!


---
### 3. ViewModel의 사용
[참고링크](https://readystory.tistory.com/176)

#### 1. 파라미터가 없는 ViewModel - Lifecycle Extensions

- 가장 편한 방법중 하나, androidx.lifecycle의 모듈을 가져와서 사용하면 됨

- step1. module 수준의 build.gradle에 디펜던시 추가
```
dependencies {
    // ...
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
}
```

- step2. ViewModel 클래스 정의
```kotlin
  class NoParamViewModel : ViewModel()
```

- step3. 액티비티에서 뷰모델 객체 생성
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamViewModel: NoParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        /* use ViewModelProvider's constructor provided from lifecycle-extensions package */
        noParamViewModel = ViewModelProvider(this).get(NoParamViewModel::class.java)
    }
}
//this는 ViewModelStoreOwner 타입인데 액티비티, 프래그먼트 사용가능
```

#### 2. 파라미터가 없는 ViewModel - ViewModelProvider.NewInstanceFactory

- NewInstanceFactory는 안드로이드가 기본적으로 제공해주는 팩토리 클래스, ViewModelProvider.Factory 인터페이스를 구현하고 있음. 따라서 ViewModel 클래스가 파라미터를 필요로 하지 않거나, 특별히 팩토리를 커스텀 할 필요가 없는 상황에서는 1번 방법을 사용하거나, 2번 방법을 사용하면 되겠습니다. (커스텀 안할거면 1번쓰기?)

```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamViewModel: NoParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        noParamViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(NoParamViewModel::class.java)
    }
}
```

#### 3. 파라미터가 없는 ViewModel - ViewModelProvider.Factory

- 팩토리를 직접 구현하는 방법인데 장점은 하나의 팩토리로 다양한 ViewModel 클래스를 관리할 수도 있고, 원치 않는 상황에 대해서 컨트롤 할 수 있다.

```kotlin
class NoParamViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NoParamViewModel::class.java)) {
            NoParamViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
```

<aside>
💡 위 코드는 NoParamViewModel 클래스가 아니면 IllegalArgumentException 을 던지도록 구현되어 있다. 이는 어디까지나 개발자의 마음대로 구현하면 되는 부분이며, 어떤 타입의 클래스가 전달되더라도 인스턴스를 생성하도록 구현할 수도 있다고함.
</aside>

#### 4. 파라미터가 있는 ViewModel - ViewModelProvider.Factory(3번의 연장선)

- **ViewModelProvider.Factory** 를 구현하면 파라미터를 소유하고 있는 ViewModel 객체의 인스턴스를 생성할 수 있다. 직접 구현한 Factory 클래스에 파라미터를 넘겨주어 create() 내에서 인스턴스를 생성할 때 활용하면 된다.


- step1. ViewModel 작성
```kotlin
class HasParamViewModel(val param: String) : ViewModel()
```

- step2. Factory 구현
```kotlin
class HasParamViewModelFactory(private val param: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HasParamViewModel::class.java)) {
            HasParamViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
```

- step3. Acitivty에서 사용
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var hasParamViewModel: HasParamViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        val sampleParam = "Ready Story"
 
        hasParamViewModel = ViewModelProvider(this, HasParamViewModelFactory(sampleParam))
            .get(HasParamViewModel::class.java)
    }
}
// 3,4 번에서는 파라미터 유무에 따라 별도의 팩토리 클래스를 구현하였지만, 꼭 그럴 필요 없이 하나의 팩토리 클래스로 두가지 상황을 한꺼번에 처리 가능!
```

#### 5. 파라미터가 없는 AndroidViewModel - AndroidViewModelFactory

- 사실 developer 사이트에 의하면 ViewModel 클래스에서 Context 객체를 소유하거나 접근하는 것에 있어서 권장하지 않고 있다. 하지만 정말 불가피하게 필요한 경우가 있을 수 있는데, ViewModel 에서 Context 를 사용해야할 필요성이 있을 때는 **AndroidViewModel** 클래스를 사용하면 됨.
- 안드로이드에서는 이러한 AndroidViewModel 객체에 대한 생성을 위해 ViewModelProvider.AndroidViewModelFactory 라는 별도의 팩토리를 제공!

step1. ViewModel 작성
```kotlin
class NoParamAndroidViewModel(application: Application) : AndroidViewModel(application)
```

step2. AndroidViewModel 은 Application 객체를 필요로함.
이번에는 AndroidViewModelFactory 를 이용하여 뷰모델 객체를 생성한다!
```kotlin
class MainActivity : AppCompatActivity() {
 
    private lateinit var noParamAndroidViewModel: NoParamAndroidViewModel
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 
        noParamAndroidViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(NoParamAndroidViewModel::class.java)
    }
}

//AndroidViewModelFactory는 내부에 NewInstanceFactory를 상속했다.
```

#### 6. 파라미터가 있는 AndroidViewModel

- **파라미터가 있는** **AndroidViewModel** 객체를 생성하는 방법이다.
- 사실 4번의 방법으로도 가능하지만, 5번에서 살펴본 AndroidViewModelFactory 와 유사한 방식으로 커스텀 팩토리를 구현해보는 방법이다.

- step1. 먼저 파라미터가 있는 AndroidViewModel 클래스를 준비한다.
```kotlin
class HasParamAndroidViewModel(application: Application, val param: String)
    : AndroidViewModel(application)
```

- step2. Custom Factory를 구현
```kotlin
class HasParamAndroidViewModelFactory(private val application: Application, private val param: String)
    : ViewModelProvider.NewInstanceFactory() {//ViewModelProvider.Factory로 해도 된다고 한다.
 
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(Application::class.java, String::class.java)
                    .newInstance(application, param)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
        return super.create(modelClass)
    }
}
```

<aside>
💡 **android-ktx / fragment-ktx 모듈을 사용하면 보다 편리하게 뷰모델 인스턴스를 생성할 수도 있다고 한다.**
</aside>

---
### 4. LiveData

---
### 5. DataBinding

🔗 [공식문서](https://developer.android.com/topic/libraries/data-binding)

🔗 [참고블로그](https://salix97.tistory.com/243)

✨ 시작!
```
dataBinding {
    enabled = true
}
```

ex) TextView 위젯에 viewModel의 변수 userName을 결합

- 기존의 findViewById 호출 방식

```kotlin
findViewById<TextView>(R.id.sample_text).apply {
        text = viewModel.userName
}
```

- 데이터바인딩 - XML 파일에서 직접 위젯에 텍스트를 할당, 코틀린 코드 호출할 필요X
- `@{}`  할당 표현식에 유의!
- 이것을 `선언적(declarative) 레이아웃` 작성 이라고 함

```xml
<TextView
        android:text="@{viewmodel.userName}" />
```

- 일반적인 레이아웃을 만들되 `<layout></layout>` 태그로 래핑해줘야함
- 표현식에서 사용할 수 있는 결합 변수는 `data` 요소 안에서 정의됨!

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto">
        <data>
            <variable
                name="viewmodel"
                type="com.myapp.data.ViewModel" />
        </data>
        <ConstraintLayout... /> <!-- UI layout's root element -->
    </layout>
```

- **Activity 에서 기존의 setContentView() 함수를 DataBindingUtil.setContentView() 로 교체한다.**

DataBindingUtil class 의 객체를 생성하고, 기존의 setContentView() 를 DataBindingUtil.setContentView() 로 대체한다.

이제, data binding 을 사용하여 layout 을 관리할 수 있다.


- **결합 어댑터(Binding Adapter)**

모든 레이아웃 표현식에는 속성 또는 리스너를 설정하는 데 필요한 프레임워크를 호출하는 **결합 어댑터가 존재한다!**

예를 들어 결합 어댑터는 `setText()` 메서드를 호출하여 텍스트 속성을 설정하거나,  `setOnClickListener()` 메서드를 호출하여 리스너를 클릭 이벤트에 추가할 수 있는데,,,

이 페이지 예시에 사용 된 `android:text` 속성의 어댑터와 같은 가장 일반적인 결합 어댑터는 `android.databinding.adapters` 패키지에서 사용할 수 있다.

- 일반적인 결합 어댑터 목록은 [어댑터](https://android.googlesource.com/platform/frameworks/data-binding/+/refs/heads/studio-master-dev/extensions/baseAdapters/src/main/java/androidx/databinding/adapters) 를 참조!!

아래와 같이 커스텀 어댑터도 생성 가능하다.

```kotlin
@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
```

> 데이터 바인딩을 왜 사용할까?
> 

**데이터 바인딩을 사용하면, 데이터를 UI 요소에 연결하기 위해 필요한 코드를 최소화할 수 있다.**

data binding 을 사용했을 때, 당장 가시적으로 보이는 장점들을 꼽자면 다음과 같다.

- findViewId() 를 호출하지 않아도, 자동으로 xml 에 있는 VIew 들을 만들어준다.
- RecyclerView 에 각각의 item 을 set 해주는 작업도 자동으로 진행된다.
- data 가 바뀌면 자동으로 View 를 변경하게 할 수 있다.
- xml 리소스만 보고도 View 에 어떤 데이터가 들어가는지 파악이 가능하다.
- 코드 가독성이 좋아지고, 상대적으로 코드량이 줄어든다.

하지만 데이터 바인딩은 클래스 파일이 많이 생기고, 빌드 속도가 느려지는 등 단점이 존재하기 때문에 단독으로 사용하기보다는,

**MVP 또는 MVVM 패턴을 구현하기 위해 데이터 바인딩이 유용하게 사용된다**


---
### 6. Coroutine

- 코루틴과 레트로핏을 함께 쓰기
[참고블로그-Retrofit-With-Coroutine](https://zladnrms.tistory.com/16)

- coroutine을 사용하지 않을 땐, 네트워크 비동기 처리를 위해 retrofit2를 사용하였고, response data의 stream 처리를 위해서는 RxJava2를 사용했다고 한다.
- 비동기 처리가 필요한 부분, 특히 서버와의 API 통신 부분을 coroutine으로 교체해보자!

🏠 가게 목록 API를 GET Method로 가져오는 상황

1. API 호출을 위한 Retrofit 인터페이스를 구현

```kotlin
// RetrofitInterface.kt
@GET("xxx/xxx")
suspend fun getStoreList(
	@Header("x-access-token") token: String,
): StoreListResponse
```

- retrofit 2.6.0 버전 이전에는 API 요청에 대한 응답을 반환받을 때 Response<T>가 필수였는데, coroutine의 suspend modifier 업데이트 이후 retrofit2에서도 변경이 있는데,,,
- `suspend` 식별자를 붙이면 Response가 필수가 아니게 된 것! _[ref](https://stackoverflow.com/questions/43021816/difference-between-thread-and-coroutine-in-kotlin)
- 그래서 API 호출 후 데이터 클래스로 바로 반환받는 것이 가능하게 되었고, 이는 API 호출 결과 처리에 있어서 기존의 상용구코드, Boiler Plate들을 줄여준다는 것에 있어서 의미가 ⬆️
- 요약하면, coroutine은 thread위에서 동작하는데, suspend 수식어가 붙은 함수가 실행되면 그 직후부터 그 함수가 끝나거나 값을 반환할때까지, thread를 block시키지 않고 suspend(지연)시키는 것. 즉,  그 함수가 호출되면 해당 함수로 Context Switching이 즉시 실행되는 것이 아니라, 우선 지연되어 특정한 시점에 호출되어 처리하는 것이다!

<aside>
💡 이러한 방법은 block에 비해 cost가 상대적으로 free하다고 하는데,,!

</aside>

1. API를 호출하여 처리하는 ViewModel의 로직

```kotlin
//StoreViewModel.kt
class StoreViewModel(private val repository: UserRepository, private val api: RetrofitInterface) : DisposableViewModel() {

    private val user by lazy {
        viewModelScope.async(Dispatchers.IO) {
            repository.getUserInfo()
        }
    }

    // recyclerview list
    val storeList = MutableLiveData<ArrayList<Store>>().apply { value = arrayListOf() }
    // empty list
    val emptyList : LiveData<Boolean> get() = Transformations.map(storeList) {
        it.isEmpty()
    }
    // is True = ProgressView VISIBLE , is False = ProgressView GONE
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    // ViewModel의 생성과 함께 API 호출
    init {
        getStoreList()
    }
    
    // 가게 목록을 가져오는 함수
    fun getStoreList() {
        // Progress View VISIBLE
        _dataLoading.value = true
        // StoreList 초기화
        storeList.value?.clear()

        // 비동기 처리 Scope 선언
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //user.key = token XXXXXXXXXXXXXXXX
                api.getStoreList(user.await().key.tokenize()).apply {
                    if (this.results.size > 0) {
                        storeList.value?.addAll(this.results)
                    }

                    withContext(Dispatchers.Main) {
                        storeList.value = storeList.value
                        // Progress View GONE
                        _dataLoading.value = false
                    }
                }
            } catch (e: Throwable) {
                ...
            }
        }
    }
```

- 변수 설명
    - `DisposableViewModel` : ViewModel을 상속하며 LifeCycle에 맞추어 RxJava의 Observable 구독 해제를 담당하는 역할이 추가됨
    - `repository: UserRepository` : 회원 정보를 담고 있는 Local DB
    - `api: RetrofitInterface` : API 요청을 위한 Retrofit Interface
    - `storeList` : Store data class를 List로 가지고 있는 객체를 가지고 있는 LiveData 클래스, RecyclerView의 List로써 쓰인다.
    - `fun getStoreList()` : 서버로 부터 Store의 목록을 받아오는 비동기 네트워크 처리 함수.
    - `dataLoading` : 비동기 처리 중 화면에 Loading Progress를 표시할 View의 VISIBLE 처리를 위한 LiveData객체.
    - `viewModelScope` : ViewModel 내에서 **Coroutine을 처리하기 위한 Scope** 선언
    - `repository.getUserInfo()` : Local DB에 저장된 유저의 데이터를 사용하기 위한 함수. 여기서는 회원의 Token 값을 사용하기 위해 호출함.
    - `api.getStoreList()` : Remote DB와 연동하기 위한 함수. 서버에서 유저의 Store 목록을 가져온다
    
    <aside>
    💡 메모리 누수 방지를 위해 LifeCycle Destroyed에 맞추어 구독을 해지하는 것을, ViewModel 상속으로 구현한 것이 **DisposableViewModel**
    
    </aside>
    
    ```kotlin
    open class DisposableViewModel: ViewModel() {
    
        /**
         * Observable의 Disposable 객체를 모아두는 클래스.
         * ViewModel이 clear될 때, 한 번에 구독해지하는 역할을 담당함.
         */
        private val compositeDisposable = CompositeDisposable()
    
        fun addDisposable(disposable: Disposable) {
            compositeDisposable.add(disposable)
        }
    
        override fun onCleared() {
            compositeDisposable.clear() // or dispose()
            super.onCleared()
        }
    }
    ```
    

또한 ViewModel 예시의 viewModelScope 안에서는 try/catch를 이용하여 예외처리를 하고있는데, viewModel 내에서 수많은 비동기 처리와 그에따른 예외 처리를 할때마다  try/catch를 사용한다면 이는 분명 **Boiler Plate**이다.

- Boiler Plate Code : 재사용 가능한 프로그램, 템플릿 코드 환경 등을 의미

[android에서 boiler plate code를 피하기](https://charlezz.medium.com/%EB%B3%B4%EC%9D%BC%EB%9F%AC%ED%94%8C%EB%A0%88%EC%9D%B4%ED%8A%B8-%EC%BD%94%EB%93%9C%EB%9E%80-boilerplate-code-83009a8d3297)

그래서 Coroutine은 CoroutineScope 내부의 예외처리 Handler을 제공하고 있다!

**[CoroutineExceptionHandler](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-exception-handler/)**이라는 녀석✨

- 위의 DisposableViewModel에 CoroutineExceptionHandler를 추가한 모습
- CoroutineScope 내에서 발생한 Exception을 Catch 해주어 coroutineContext와 Throwable의 형태로 반환
- Dispatchers.IO와 Dispatchers.Main 등 사용할 쓰레드에 위의 핸들러를 추가하여 따로 변수를 만들었다.

```kotlin
open class DisposableViewModel: ViewModel() {

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    protected val coroutineExceptionHanlder = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    /**
     * Dispatchers 선언 (Normal Dispatchers + CoroutineExceptionHandler)
     */
    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder

    /**
     * Clear Rx when called onCleared
     */
    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
```

- DisposableViewModel을 상속받은 StoreViewModel

```kotlin
//StoreViewModel.kt
class StoreViewModel(private val repository: UserRepository, private val api: NetworkInterface) : DisposableViewModel() {

    private val user by lazy {
        viewModelScope.async(ioDispatchers) {
            repository.getUserInfo()
        }
    }

    // recyclerview list
    val storeList = MutableLiveData<ArrayList<Store>>().apply { value = arrayListOf() }
    // empty list
    val emptyList : LiveData<Boolean> get() = Transformations.map(storeList) {
        it.isEmpty()
    }
    // is True = ProgressView VISIBLE , is False = ProgressView GONE
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    // ViewModel의 생성과 함께 API 호출
    init {
        getStoreList()
    }
    
    // 가게 목록을 가져오는 함수
    fun getStoreList() {
        // Progress View VISIBLE
        _dataLoading.value = true
        // StoreList 초기화
        storeList.value?.clear()

        // 비동기 처리 Scope 선언
        viewModelScope.launch(ioDispatchers) {
            api.getStoreList(user.await().key.tokenize()).apply {
                if (this.results.size > 0) {
                    storeList.value?.addAll(this.results)
                }

                withContext(uiDispatchers) {
                    storeList.value = storeList.value
                    // Progress View GONE
                    _dataLoading.value = false
                }
            }
        }
    }
}
```
    
---
### 7. Navigation

🔗 [공식문서](https://developer.android.com/guide/navigation?hl=ko)

#### build.gradle에 추가
```kotlin
dependencies {
  val nav_version = "2.4.1"

  // Java language implementation
  implementation("androidx.navigation:navigation-fragment:$nav_version")
  implementation("androidx.navigation:navigation-ui:$nav_version")

  // Kotlin
  implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
  implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

  // Feature module Support
  implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

  // Testing Navigation
  androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

  // Jetpack Compose Integration
  implementation("androidx.navigation:navigation-compose:$nav_version")
}
```

#### 탐색 그래프(nav_graph) 만들기
    - 탐색 그래프는 모든 대상 및 작업을 포함하는 resource(xml) 파일
    - 그래프는 앱의 모든 navigation 경로를 나타내 줌


ex)

<img width="500" alt="image" src="https://user-images.githubusercontent.com/85485290/161588772-c34c3931-fb16-42b8-b815-024bfdf7dcd8.png">

> 1. Project의 ```res``` 디렉토리에서 New > Android Resource File
> 
> 2. File name에 'nav_graph'와 같은 이름 입력
> 
> 3. Resource type으로 Navigation을 선택하고 OK
> 

> 첫번째 nav_graph를 추가할 때는 ```res``` 디렉토리 안에 ```navigation``` 리소스 디렉토리를 만들고 여기에 ```nav_graph.xml```을 만들어야 함!!

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/nav_graph">
</navigation>
```
중첩된 그래프가 있다면 하위 <navigation> 요소로 표시될 것

#### NavHost 추가
    - 탐색 호스트는 빈 컨테이너로, 사용자가 앱을 탐색하는 동안 여기서 대상이 교체된다.
    - 탐색 호스트는 ```NavHost```에서 파생되어야 함
    - 탐색 구성요소의 기본 ```NavHost``` 구현인 ```NavHostFragment```는 프래그먼트 대상의 교체를 처리함
    
    
#### XML을 통한 NavHostFragment 추가
    - 이건 가장 밑단의 base activity에 들어갈 내용인듯? 하나의 액티비티 - 다수의 프래그먼트 구조
    
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.Toolbar
        .../>

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host_fragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"

        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        .../>

</androidx.constraintlayout.widget.ConstraintLayout>
```
- android:name 속성은 NavHost 구현의 클래스 이름을 포함.
- app:navGraph 속성은 NavHostFragment를 탐색 그래프와 연결. 탐색 그래프는 사용자가 이동할 수 있는 이 NavHostFragment의 모든 대상을 지정.
- app:defaultNavHost="true" 속성을 사용하면 NavHostFragment가 시스템 뒤로 버튼을 가로챔? 하나의 NavHost만 기본값으로 지정할 수 있음. 동일한 레이아웃에 여러 호스트가 있다면(예: 창이 2개인 레이아웃) 한 호스트만 기본 NavHost로 지정해야 함.
    
    
#### 탐색 그래프에 대상 추가
    
[참고](https://developer.android.com/guide/navigation/navigation-create-destinations?hl=ko)
 
<img width="300" alt="image" src="https://user-images.githubusercontent.com/85485290/161591913-2e53c359-47b2-4708-9b9d-56305272fa13.png">

#### 대상의 구성
    - **Type** 필드는 대상이 소스 코드에서 프래그먼트, 활동 또는 기타 클래스로 구현되는지 여부를 나타냄
    - **Lable** 필드는 사용자가 읽을 수 있는 대상 이름임 -> res의 string 문자열을 사용하는 것이 좋음
    - **ID** 필드에는 대상을 참조할 수 있는 대상의 ID임
    - **Class** 드롭다운에는 대상과 연결된 클래스 이름이 표시됨
    
```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    app:startDestination="@id/blankFragment">
    <fragment
        android:id="@+id/blankFragment"
        android:name="com.example.cashdog.cashdog.BlankFragment"
        android:label="@string/label_blank"
        tools:layout="@layout/fragment_blank" />
</navigation> 
```
    
#### 대상 연결

- ```<action>``` 태그에 연결을 나타낼 수 있음!

```
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    app:startDestination="@id/blankFragment">
    <fragment
        android:id="@+id/blankFragment"
        android:name="com.example.cashdog.cashdog.BlankFragment"
        android:label="@string/label_blank"
        tools:layout="@layout/fragment_blank" >
        <action
            android:id="@+id/action_blankFragment_to_blankFragment2"
            app:destination="@id/blankFragment2" />
    </fragment>
    <fragment
        android:id="@+id/blankFragment2"
        android:name="com.example.cashdog.cashdog.BlankFragment2"
        android:label="@string/label_blank_2"
        tools:layout="@layout/fragment_blank_fragment2" />
</navigation>
```
    
#### 대상으로 이동
대상으로 이동하는 것은 ```NavController``` 객체를 사용하여 실행되며 이 객체는 ```NavHost``` 내에서 앱 탐색을 관리한다. 각 NavHost에는 해당하는 자체 NavController가 있는데, 다음 메서드 중 하나를 사용하여 NavController를 검색한다.

**Kotlin:**    
    - Fragment.findNavController()
    - View.findNavController()
    - Activity.findNavController(viewId: Int)
    
> FragmentContainerView를 사용하여 NavHostFragment를 만들 때 & FragmentTransaction을 통해 NavHostFragment를 활동에 수동으로 추가할 경우, Navgation.findNavController(Activity, @IdRes int)를 통해 활동의 onCreate()에서 NavController를 검색하려고 하면 **실패**한다. 대신 NavHostFragment에서 직접 NavController를 검색해야 함!

```
val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
val navController = navHostFragment.navController
```

⨁ Safe Args를 사용한 대상간의 이동을 위한 안전성 보장
    
[Safe Args를 사용하여 유형 안전성을 갖춘 데이터 전달](https://developer.android.com/guide/navigation/navigation-pass-data?hl=ko#Safe-args)
    
- build.gradle에 classpath 포함
```
    buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.4.1"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
```
    
- build.gradle(module)에 플러그인 적용
```
plugins {
    id("androidx.navigation.safeargs")
}
    
// or
    
plugins {
    id("androidx.navigation.safeargs.kotlin")
}
    
```
    
- gradle.properties 파일에 ```android.useAndroidX=true```가 있어야 함.
    
```
    override fun onClick(view: View) {
    val action =
        SpecifyAmountFragmentDirections
            .actionSpecifyAmountFragmentToConfirmationFragment()
    view.findNavController().navigate(action)
}
```
    
    
    
    
