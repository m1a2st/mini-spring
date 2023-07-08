## 練習Spring框架

---------------

# IOC

## 最簡單的Bean容器

先定義一個bean容器的**`BeanFactory`**，內部包含一個map用來保存bean，只有註冊bean和獲取bean兩個方法

![CleanShot 2023-06-30 at 20.19.55@2x.png](img%2FCleanShot%202023-06-30%20at%2020.19.55%402x.png)

```java
public class BeanFactory {

  private final Map<String, Object> beanMap = new HashMap<>();

  public void registerBean(String beanName, Object bean) {
    beanMap.put(beanName, bean);
  }

  public Object getBean(String beanName) {
    return beanMap.get(beanName);
  }
}
```

測試：

```java
public class BeanFactoryTest {

  @Test
  public void test_bean_factory() {
    BeanFactory beanFactory = new BeanFactory();
    beanFactory.registerBean("helloService", new HelloService());
    HelloService helloService = (HelloService) beanFactory.getBean("helloService");
    assertNotNull(helloService);
    assertEquals(helloService.sayHello(), "Hello");
  }

  static class HelloService {
    public String sayHello() {
      return "Hello";
    }
  }
}
```

## BeanDefinition和BeanDefinitionRegistry

### 主要增加如下類：

- **`BeanDefinition`**
  ，顧名思義，用於定義bean信息的類，包含bean的class類型、構造參數、屬性值等信息，每個bean對應一個**`BeanDefinition`**的實例。簡化
  **`BeanDefinition`**僅包含bean的class類型。
- **`BeanDefinitionRegistry`**，**`BeanDefinition`**注冊表介面，定義注冊**`BeanDefinition`**的方法。
  **`SingletonBeanRegistry`**及其實現類**`DefaultSingletonBeanRegistry`**，定義添加和獲取單例bean的方法。
- bean容器作為**`BeanDefinitionRegistry`**和**`SingletonBeanRegistry`**
  的實現類，具備兩者的能力。向bean容器中注冊BeanDefinition後，使用bean時才會實例化。

![CleanShot 2023-06-30 at 22.08.52@2x.png](img%2FCleanShot%202023-06-30%20at%2022.08.52%402x.png)

```java
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

  private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

  @Override
  public Object getSingleton(String beanName) {
    return singletonObjects.get(beanName);
  }

  protected void addSingleton(String beanName, Object singletonObject) {
    singletonObjects.put(beanName, singletonObject);
  }
}
```

```java
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

  @Override
  public Object getBean(String beanName) throws BeansException {
    Object bean = getSingleton(beanName);
    if (bean != null) {
      return bean;
    }
    BeanDefinition beanDefinition = getBeanDefinition(beanName);
    return createBean(beanName, beanDefinition);
  }

  protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

  protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
```

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

  @Override
  protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    return doCreateBean(beanName, beanDefinition);
  }

  protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    Class<?> beanClass = beanDefinition.getBeanClass();
    Object bean = null;
    // 通過反射創建bean實例
    try {
      // 獲取無參構造函數
      Constructor<?> constructor = beanClass.getDeclaredConstructor();
      // 設置訪問權限
      constructor.setAccessible(true);
      // 創建bean實例
      bean = constructor.newInstance();
    } catch (Exception e) {
      throw new BeansException("Instantiation of bean failed", e);
    }
    addSingleton(beanName, bean);
    return bean;
  }
}
```

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

  @Override
  protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    if (beanDefinition == null) {
      throw new BeansException("No bean named '" + beanName + "' is defined");
    }
    return beanDefinition;
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanDefinitionMap.put(beanName, beanDefinition);
  }
}
```

### 測試

```java
public class BeanFactoryTest {

  @Test
  public void test_bean_factory() throws BeansException {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
    beanFactory.registerBeanDefinition("helloService", beanDefinition);
    HelloService helloService = (HelloService) beanFactory.getBean("helloService");
    assertNotNull(helloService);
    assertEquals(helloService.sayHello(), "Hello");
  }

  static class HelloService {
    public String sayHello() {
      return "Hello";
    }
  }
}
```

## Bean實例化策略

### Force

![CleanShot 2023-06-30 at 23.01.02@2x.png](img%2FCleanShot%202023-06-30%20at%2023.01.02%402x.png)

### Resulting Context

![CleanShot 2023-06-30 at 23.03.37@2x.png](img%2FCleanShot%202023-06-30%20at%2023.03.37%402x.png)

目前bean是在**`AbstractAutowireCapableBeanFactory.doCreateBean()`**方法裡面使用反射來實例化，但只適用於無參建構子

針對bean的實例化，我們抽出一個介面，並且使用strategy Pattern來實作不同的實例化策略

- **`SimpleInstantiationStrategy`**，使用bean的構造函數來實例化
- **`CglibSubclassingInstantiationStrategy`**，使用CGLIB動態生成子類

### Maven

```xml

<dependency>
  <groupId>cglib</groupId>
  <artifactId>cglib</artifactId>
  <version>3.3.0</version>
</dependency>
```

### Strategy

```java
public interface InstantiationStrategy {

  Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
```

```java
public class SimpleInstantiationStrategy implements InstantiationStrategy {

  @Override
  public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
    Class<?> beanClass = beanDefinition.getBeanClass();
    try {
      Constructor<?> constructor = beanClass.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (Exception e) {
      throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
    }
  }
}
```

```java
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

  @Override
  public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(beanDefinition.getBeanClass());
    enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));
    return enhancer.create();
  }
}
```

### 依賴反轉

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

  private final InstantiationStrategy instantiationStrategy;

  public AbstractAutowireCapableBeanFactory(InstantiationStrategy instantiationStrategy) {
    this.instantiationStrategy = instantiationStrategy;
  }

  @Override
  protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    return doCreateBean(beanName, beanDefinition);
  }

  protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    Object bean;
    try {
      bean = createBeanInstance(beanDefinition);
    } catch (Exception e) {
      throw new BeansException("Instantiation of bean failed", e);
    }
    addSingleton(beanName, bean);
    return bean;
  }

  private Object createBeanInstance(BeanDefinition beanDefinition) throws BeansException {
    return instantiationStrategy.instantiate(beanDefinition);
  }
}
```

### 測試

```java
public class BeanFactoryTest {

  @Test
  public void testBeanFactory_withSimpleInstantiationStrategy() throws BeansException {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(new SimpleInstantiationStrategy());
    BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
    beanFactory.registerBeanDefinition("helloService", beanDefinition);
    HelloService helloService = (HelloService) beanFactory.getBean("helloService");
    assertNotNull(helloService);
    assertEquals(helloService.sayHello(), "Hello");
  }

  @Test
  public void testBeanFactory_withCglibSubclassingInstantiationStrategy() throws BeansException {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(new CglibSubclassingInstantiationStrategy());
    BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
    beanFactory.registerBeanDefinition("helloService", beanDefinition);
    HelloService helloService = (HelloService) beanFactory.getBean("helloService");
    assertNotNull(helloService);
    assertEquals(helloService.sayHello(), "Hello");
  }

  static class HelloService {
    public String sayHello() {
      return "Hello";
    }
  }
}
```

## 為Bean注入Bean

增加BeanReference類，包裝一個bean對另一個bean的引用。實例化beanA後填充屬性時，若PropertyValue#value為BeanReference，引用beanB，則先去實例化beanB。
由於不想增加代碼的覆雜度提高理解難度，暫時不支持循環依賴

### UML和流程圖

![CleanShot 2023-07-01 at 10.46.39@2x.png](img%2FCleanShot%202023-07-01%20at%2010.46.39%402x.png)
![image.png](img%2Fimage.png)

```xml

<dependency>
  <groupId>cn.hutool</groupId>
  <artifactId>hutool-all</artifactId>
  <version>5.8.20</version>
</dependency>
```

### 先新增Bean的屬性class

```java
public class PropertyValue {

  private final String name;
  private final Object value;

  public PropertyValue(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public Object getValue() {
    return this.value;
  }
}
```

```java
public class PropertyValues {

  private final List<PropertyValue> propertyValueList = new ArrayList<>();

  public void addPropertyValue(PropertyValue pv) {
    propertyValueList.add(pv);
  }

  public PropertyValue[] getPropertyValues() {
    return this.propertyValueList.toArray(new PropertyValue[0]);
  }

  public PropertyValue getPropertyValue(String propertyName) {
    for (PropertyValue pv : this.propertyValueList) {
      if (pv.getName().equals(propertyName)) {
        return pv;
      }
    }
    return null;
  }
}
```

### Bean的定義增加屬性

```java
/**
 * BeanDefinition實例保存bean的信息，包括class類型、
 * 方法構造參數、是否為單例等，此處簡化只包含class類型跟bean屬性
 *
 * @Author m1a2st
 * @Date 2023/6/30
 * @Version v1.0
 */
public class BeanDefinition {

  private Class<?> beanClass;
  private PropertyValues propertyValues;

  public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
    this.beanClass = beanClass;
    this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
  }

  public BeanDefinition(Class<?> beanClass) {
    this(beanClass, null);
  }

  public Class<?> getBeanClass() {
    return beanClass;
  }

  public void setBeanClass(Class<?> beanClass) {
    this.beanClass = beanClass;
  }

  public PropertyValues getPropertyValues() {
    return propertyValues;
  }

  public void setPropertyValues(PropertyValues propertyValues) {
    this.propertyValues = propertyValues;
  }
}
```

### 新增為Bean添加屬性

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

  private final InstantiationStrategy instantiationStrategy;

  public AbstractAutowireCapableBeanFactory(InstantiationStrategy instantiationStrategy) {
    this.instantiationStrategy = instantiationStrategy;
  }

  @Override
  protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    return doCreateBean(beanName, beanDefinition);
  }

  protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
    Object bean;
    try {
      // 創造Bean 實體
      bean = createBeanInstance(beanDefinition);
      // 為Bean 填充屬性
      addPropertyValue(beanName, bean, beanDefinition);
    } catch (Exception e) {
      throw new BeansException("Instantiation of bean failed", e);
    }
    addSingleton(beanName, bean);
    return bean;
  }

  /**
   * 為Bean 填充屬性
   * @param beanName bean名稱
   * @param bean bean實例
   * @param beanDefinition bean定義
   */
  private void addPropertyValue(String beanName, Object bean, BeanDefinition beanDefinition) throws BeansException {
    try {
      for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
        String name = propertyValue.getName();
        Object value = propertyValue.getValue();
        if (value instanceof BeanReference beanReference) {
          value = getBean(beanReference.getBeanName());
        }
        // 通過反射設置屬性值
        BeanUtil.setFieldValue(bean, name, value);
      }
    } catch (BeansException e) {
      throw new BeansException("Error setting property values for bean: " + beanName, e);
    }
  }

  private Object createBeanInstance(BeanDefinition beanDefinition) throws BeansException {
    return instantiationStrategy.instantiate(beanDefinition);
  }
}
```

```java
public class BeanReference {

  public final String beanName;

  public BeanReference(String beanName) {
    this.beanName = beanName;
  }

  public String getBeanName() {
    return beanName;
  }
}
```

### 測試

```java
public class Car {

  private String brand;
  // 注意要有空的建構子
}
```

```java
public class Person {

  private String name;
  private int age;
  private Car car;
}
```

這個是測試外部為bean注入屬性

```java
public class PopulateBeanWithPropertyValuesTest {

  @Test
  public void testPopulateBeanWithPropertyValues() throws BeansException {
    // 1. 創建 BeanFactory 容器
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 2. 創建 BeanDefinition
    PropertyValues propertyValues = new PropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("name", "m1a2st"));
    propertyValues.addPropertyValue(new PropertyValue("age", 18));
    // 3. 為 BeanFactory 容器注册 BeanDefinition
    BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
    beanFactory.registerBeanDefinition("person", beanDefinition);
    // 4. 獲取 Bean
    Person person = (Person) beanFactory.getBean("person");
    // 5. 驗證結果
    assertEquals(person.getName(), "m1a2st");
    assertEquals(person.getAge(), 18);
  }
}
```

因發現每次做測試創建beanFactory時都要注入**`SimpleInstantiationStrategy`**，所以做些修改，把他預設策略定為
**`SimpleInstantiationStrategy`**，有需要替換在外部抽換

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

  private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

  public AbstractAutowireCapableBeanFactory(InstantiationStrategy instantiationStrategy) {
    this.instantiationStrategy = instantiationStrategy;
  }

  public AbstractAutowireCapableBeanFactory() {
  }
  // 省略
}
```

測試bean依賴bean

```java
@Test
public void testPopulateBeanWithBean()throws BeansException{
        // 1. 創建 BeanFactory 容器
        DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
        // 2. 創建 Car BeanDefinition
        PropertyValues propertyValuesForCar=new PropertyValues();
        propertyValuesForCar.addPropertyValue(new PropertyValue("brand","porsche"));
        BeanDefinition carBeanDefinition=new BeanDefinition(Car.class,propertyValuesForCar);
        beanFactory.registerBeanDefinition("car",carBeanDefinition);
        // 2. 創建 Person BeanDefinition
        PropertyValues propertyValuesForPerson=new PropertyValues();
        propertyValuesForPerson.addPropertyValue(new PropertyValue("name","m1a2st"));
        propertyValuesForPerson.addPropertyValue(new PropertyValue("age",18));
        // Person 實例依賴 Car 實例
        propertyValuesForPerson.addPropertyValue(new PropertyValue("car",new BeanReference("car")));
        BeanDefinition beanDefinition=new BeanDefinition(Person.class,propertyValuesForPerson);
        beanFactory.registerBeanDefinition("person",beanDefinition);
        // 3. 獲取 Bean
        Person person=(Person)beanFactory.getBean("person");
        // 4. 驗證結果
        assertEquals(person.getName(),"m1a2st");
        assertEquals(person.getAge(),18);
        assertEquals(person.getCar().getBrand(),"porsche");
        }
```

## 資源合資源加載器

![CleanShot 2023-07-02 at 11.38.21@2x.png](img%2FCleanShot%202023-07-02%20at%2011.38.21%402x.png)

- FileSystemResource，文件系統資源的實現類
- ClassPathResource，classpath下資源的實現類
- UrlResource，對 java.net.URL進行資源定位的實現類
- ResourceLoader介面則是資源查找定位策略的抽象，DefaultResourceLoader是其默認實現類

![CleanShot 2023-07-02 at 11.51.58@2x.png](img%2FCleanShot%202023-07-02%20at%2011.51.58%402x.png)

### Resource

```java
public interface Resource {

  InputStream getInputStream() throws IOException;
}
```

```java
public class FileSystemResource implements Resource {

  private final String filePath;

  public FileSystemResource(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    try {
      Path path = new File(filePath).toPath();
      return Files.newInputStream(path);
    } catch (NoSuchFileException e) {
      throw new NoSuchFileException(e.getMessage());
    }
  }
}
```

```java
public class ClassPathResource implements Resource {

  private final String path;

  public ClassPathResource(String path) {
    this.path = path;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
    if (inputStream == null) {
      throw new IOException(path + " cannot be opened because it does not exist");
    }
    return inputStream;
  }
}
```

```java
public class UrlResource implements Resource {

  private final URL url;

  public UrlResource(URL url) {
    this.url = url;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    URLConnection conn = url.openConnection();
    return conn.getInputStream();
  }
}
```

### ResourceLoader

```java
public interface ResourceLoader {

  Resource getResource(String location);
}
```

```java
public class DefaultResourceLoader implements ResourceLoader {

  public static final String CLASSPATH_URL_PREFIX = "classpath:";

  @Override
  public Resource getResource(String location) {
    if (location.startsWith(CLASSPATH_URL_PREFIX)) {
      return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
    } else {
      // 嘗試當成Url處理
      try {
        return new UrlResource(new URL(location));
      } catch (MalformedURLException ex) {
        // 當成文件系統下的資源處理
        return new FileSystemResource(location);
      }
    }
  }
}
```

### 測試

```java
@Test
public void testResourceLoader()throws IOException{
        DefaultResourceLoader resourceLoader=new DefaultResourceLoader();
        // 測試加載classpath下的資源
        Resource resource=resourceLoader.getResource("classpath:hello.txt");
        InputStream inputStream=resource.getInputStream();
        String content=IoUtil.readUtf8(inputStream);
        assertEquals("hello world",content);

        // 測試加載文件系統資源
        resource=resourceLoader.getResource("src/main/test/resources/hello.txt");
        assertTrue(resource instanceof FileSystemResource);
        inputStream=resource.getInputStream();
        content=IoUtil.readUtf8(inputStream);
        assertEquals("hello world",content);

        // 測試加載url資源
        resource=resourceLoader.getResource("https://www.google.com");
        assertTrue(resource instanceof UrlResource);
        inputStream=resource.getInputStream();
        content=IoUtil.readUtf8(inputStream);
        assertTrue(content.contains("google"));
        }
```

## 在 xml文件定義 bean

有了資源加載器，就可以在xml格式配置文件中聲明式地定義bean的信息，資源加載器讀取xml文件，解析出bean的信息，然後往容器中注冊**`BeanDefinition`**。

**`BeanDefinitionReader`**是讀取bean定義信息的抽象接口，**`XmlBeanDefinitionReader`**是從xml文件中讀取的實現類。
**`BeanDefinitionReader`**需要有獲取資源的能力，且讀取bean定義信息後需要往容器中注冊**`BeanDefinition`**
，因此**`BeanDefinitionReader`**的抽象實現類**`AbstractBeanDefinitionReader`**擁有**`ResourceLoader`**和
**`BeanDefinitionRegistry`**兩個屬性。

由於從xml文件中讀取的內容是String類型，所以屬性僅支持String類型和引用其他Bean。後面會講到類型轉換器，實現類型轉換。

為了方便後面的講解和功能實現，並且盡量保持和spring中**`BeanFactory`**的繼承層次一致，對**`BeanFactory`**的繼承層次稍微做了調整。

## 整體架構

![CleanShot 2023-07-04 at 22.21.43.png](img%2FCleanShot%202023-07-04%20at%2022.21.43.png)

## 重點架構

這次增加的新功能我認為的重點是紅圈處，總共有三個地方

1. **`AbstractBeanDefinitionReader`**：最主要把bean工廠跟資源讀取器銜接在一起的類
2. **`BeanDefinitionRegistry`**介面跟他的繼承類**`DefaultListableBeanFactory`**：bean工廠的實現類
3. `**ResourceLoader**`跟他的實現類**`DefaultResourceLoader`**：用來讀取檔案

![CleanShot 2023-07-04 at 22.34.05.png](img%2FCleanShot%202023-07-04%20at%2022.34.05.png)

## 這一次改動的介面

```java
public interface BeanDefinitionReader {

  BeanDefinitionRegistry getRegistry();

  ResourceLoader getResourceLoader();

  void loadBeanDefinitions(String location) throws BeansException;

  void loadBeanDefinitions(String[] locations) throws BeansException;

  void loadBeanDefinitions(Resource resource) throws BeansException;
}
```

```java
public interface BeanDefinitionRegistry {

  /**
   * 向註冊表註冊BeanDefinition
   *
   * @param beanName       bean名稱
   * @param beanDefinition bean定義
   */
  void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

  /**
   * 是否包含指定名稱的BeanDefinition
   * @param beanName bean名稱
   * @return 是否包含
   */
  boolean containsBeanDefinition(String beanName);

  /**
   * 返回定義所有的 bean名稱
   * @return 所有的 bean名稱
   */
  String[] getBeanDefinitionNames();
}
```

```java
public interface ListableBeanFactory extends BeanFactory {

  /**
   * 返回指定類的所有實例
   *
   * @param type 指定類別
   * @param <T>  類型
   * @return 所有此類的Bean
   * @throws BeanException 當bean不存在時
   */
  <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

  /**
   * 返回所有bean的名稱
   *
   * @return 所有bean的名稱
   */
  String[] getBeanDefinitionNames();
}
```

## 實作類

```java
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

  private final BeanDefinitionRegistry registry;
  private ResourceLoader resourceLoader;

  protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
    this(registry, new DefaultResourceLoader());
  }

  public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    this.registry = registry;
    this.resourceLoader = resourceLoader;
  }

  @Override
  public BeanDefinitionRegistry getRegistry() {
    return registry;
  }

  @Override
  public void loadBeanDefinitions(String[] locations) throws BeansException {
    for (String location : locations) {
      loadBeanDefinitions(location);
    }
  }

  @Override
  public ResourceLoader getResourceLoader() {
    return resourceLoader;
  }

  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }
}
```

```java
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

  public static final String BEAN_ELEMENT = "bean";
  public static final String PROPERTY_ELEMENT = "property";
  public static final String ID_ATTRIBUTE = "id";
  public static final String NAME_ATTRIBUTE = "name";
  public static final String CLASS_ATTRIBUTE = "class";
  public static final String VALUE_ATTRIBUTE = "value";
  public static final String REF_ATTRIBUTE = "ref";

  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
    super(registry);
  }

  public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
    super(registry, resourceLoader);
  }

  @Override
  public void loadBeanDefinitions(String location) throws BeansException {
    ResourceLoader resourceLoader = getResourceLoader();
    Resource resource = resourceLoader.getResource(location);
    loadBeanDefinitions(resource);
  }

  @Override
  public void loadBeanDefinitions(Resource resource) throws BeansException {
    try {
      try (InputStream inputStream = resource.getInputStream()) {
        doLoadBeanDefinitions(inputStream);
      }
    } catch (IOException e) {
      throw new BeansException("IOException parsing XML document from " + resource, e);
    }

  }

  private void doLoadBeanDefinitions(InputStream inputStream) throws BeansException {
    Document document = XmlUtil.readXML(inputStream);
    Element root = document.getDocumentElement();
    NodeList childNodes = root.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      if (childNodes.item(i) instanceof Element) {
        if (BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
          // 解析 bean標籤
          Element bean = (Element) childNodes.item(i);
          String id = bean.getAttribute(ID_ATTRIBUTE);
          String name = bean.getAttribute(NAME_ATTRIBUTE);
          String className = bean.getAttribute(CLASS_ATTRIBUTE);

          // 加載bean的定義信息
          Class<?> clazz;
          try {
            clazz = Class.forName(className);
          } catch (ClassNotFoundException e) {
            throw new BeansException("Cannot find class [" + className + "]");
          }
          // id優先於name
          String beanName = StrUtil.isNotEmpty(id) ? id : name;
          if (StrUtil.isEmpty(beanName)) {
            // 如果id和name都為空，將類名的第一個字母轉為小寫後作為bean的名稱
            beanName = StrUtil.lowerFirst(clazz.getSimpleName());
          }
          // 創建bean定義信息
          BeanDefinition beanDefinition = new BeanDefinition(clazz);
          // 解析並設置 bean的屬性
          for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
            if (bean.getChildNodes().item(j) instanceof Element) {
              if (PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                // 判斷是否為property標籤
                Element property = (Element) bean.getChildNodes().item(j);
                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(nameAttribute)) {
                  throw new BeansException("The name attribute cannot be null or empty");
                }
                // 解析value和ref，如果都有值，則優先取ref
                Object value = valueAttribute;
                if (StrUtil.isNotEmpty(refAttribute)) {
                  value = new BeanReference(refAttribute);
                }
                // 將屬性設置到bean定義信息中
                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
              }
            }
          }
          // beanName不能重複
          if (getRegistry().containsBeanDefinition(beanName)) {
            throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
          }

          getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
      }
    }
  }
}
```

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

  private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

  public DefaultListableBeanFactory(InstantiationStrategy instantiationStrategy) {
    super(instantiationStrategy);
  }

  public DefaultListableBeanFactory() {
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanDefinitionMap.put(beanName, beanDefinition);
  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanDefinitionMap.containsKey(beanName);
  }

  @Override
  public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
    Map<String, T> result = new HashMap<>();
    beanDefinitionMap.forEach((beanName, beanDefinition) -> {
      Class<?> beanClass = beanDefinition.getBeanClass();
      if (type.isAssignableFrom(beanClass)) {
        T bean;
        try {
          bean = (T) getBean(beanName);
        } catch (BeansException e) {
          throw new BeanException("get Bean fail.", e);
        }
        result.put(beanName, bean);
      }
    });
    return result;
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return beanDefinitionMap.keySet().toArray(new String[0]);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    if (beanDefinition == null) {
      throw new BeansException("No bean named '" + beanName + "' is defined");
    }
    return beanDefinition;
  }
}
```

## 測試

```java
public class XmlFileDefinitionBeanTest {

  @Test
  public void test_xml_file() throws BeansException {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    reader.loadBeanDefinitions("classpath:spring.xml");

    Person person = (Person) beanFactory.getBean("person");
    assertEquals("m1a2st", person.getName());
    assertEquals("tesla", person.getCar().getBrand());
    Car car = (Car) beanFactory.getBean("car");
    assertEquals("tesla", car.getBrand());
  }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	         http://www.springframework.org/schema/beans/spring-beans.xsd
		 http://www.springframework.org/schema/context
		 http://www.springframework.org/schema/context/spring-context-4.0.xsd">

  <bean id="person" class="org.m1a2st.beans.bean.Person">
    <property name="name" value="m1a2st"/>
    <property name="car" ref="car"/>
  </bean>

  <bean id="car" class="org.m1a2st.beans.bean.Car">
    <property name="brand" value="tesla"/>
  </bean>
</beans>
```
