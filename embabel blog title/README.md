# Embabel Blog Title Generator

## Description
A Java-based AI agent using Embabel that extracts topics from text and generates catchy blog titles with type-safe, domain-driven design.

## Features
- 🎯 Type-safe domain modeling
- ⚡ Automatic parallel execution
- 🔄 GOAP-based intelligent planning
- 🏗️ Spring Boot integration
- 📊 Structured data output (JSON-ready)
- 🧪 Easy unit testing
- 🔧 Enterprise-grade configuration

## Installation

### Prerequisites
- Java 21+
- Maven 3.8+

### Steps

1. **Clone and navigate:**
```bash
cd embabel-blog-titler
```

2. **Set up environment:**
```bash
cp .env.example .env
# Edit .env and add your WINDSURF_API_KEY
```

3. **Build project:**
```bash
mvn clean install
```

## Usage

```bash
mvn spring-boot:run
```

Or package and run:
```bash
mvn package
java -jar target/embabel-blog-titler-1.0.0.jar
```

## Architecture

### GOAP Planning Flow
Embabel automatically infers the execution plan:
```
UserInput  extractTopics()  Topics  generateBlogTitles()  BlogTitles
```

**No manual workflow definition needed!**

### Domain Model
```java
record Topics(List topics)
record TopicTitles(String topic, List titles)
record BlogTitles(List topicTitles)
```

### Actions
1. **extractTopics**: Analyzes text and returns structured Topics
2. **generateBlogTitles**: Processes topics in parallel, returns structured BlogTitles

### Key Concepts

#### Actor
Combines LLM configuration, persona, and hyperparameters:
```java
private final Actor techWriter = new Actor<>(
    "System instruction here",
    LlmOptions.withAutoLlm()
);
```

#### Type-Safe Planning
The planner infers workflow from method signatures:
- `extractTopics` returns `Topics` 
- `generateBlogTitles` needs `Topics` and returns `BlogTitles` 
- Goal is `BlogTitles`  planner knows the order!

#### Automatic Parallelization
```java
context.parallelMap(topics.topics(), 10, topic -> { ... })
```

## Pros
✅ **Type Safety**: Compile-time checks prevent runtime errors
✅ **Domain Modeling**: Rich, structured data types
✅ **Automatic Planning**: No manual workflow definition
✅ **Parallel Execution**: Built-in concurrency support
✅ **Spring Integration**: Full enterprise framework support
✅ **Testability**: Easy to mock and unit test
✅ **Performance**: JVM optimization and true multithreading
✅ **Tooling**: IDE support, refactoring, debugging
✅ **Scalability**: Enterprise-grade architecture
✅ **Externalization**: Config via application.yml
✅ **Persistence**: Easy database integration with domain objects

## Cons
❌ More verbose than Python (but safer)
❌ Steeper learning curve (Java + Spring + Embabel)
❌ Longer compile times
❌ Smaller AI framework ecosystem compared to Python

## Key Advantages Over LangGraph

### 1. Type Safety
```java
// Embabel: Compile-time safety
public record Topics(List topics) {}

// LangGraph: Runtime strings
class State(TypedDict):
    topics: str  # Actually a list, but typed as string!
```

### 2. Domain Modeling
```java
// Embabel: Proper structure
record BlogTitles(List topicTitles)

// LangGraph: Flat strings
{"titles": "Topic 1:\n- Title A\n- Title B\n..."}
```

### 3. No Manual Workflow
```java
// Embabel: Inferred automatically from types
@Action
public BlogTitles generateBlogTitles(Topics topics) { ... }

// LangGraph: Manual workflow construction
workflow.add_edge("extract_topics", "generate_titles")
workflow.add_edge("generate_titles", END)
```

### 4. Built-in Parallelization
```java
// Embabel: One line
context.parallelMap(topics, 10, topic -> process(topic))

// LangGraph: Complex edge configuration
builder.add_edge(START, "task1")
builder.add_edge(START, "task2")
```

### 5. Enterprise Integration
```java
// Embabel: Full Spring ecosystem
@Bean
public DataSource dataSource() { ... }

// LangGraph: Manual everything
```

## Testing Example
```java
@Test
void testTopicExtraction() {
    var agent = new BlogTitlerAgent();
    var mockAi = mock(Ai.class);
    var input = new UserInput("test text");
    
    // Easy to test individual actions
    Topics result = agent.extractTopics(input, mockAi);
    
    assertNotNull(result);
    assertFalse(result.topics().isEmpty());
}
```

## Configuration
All configuration in `application.yml`:
```yaml
embabel:
  agent:
    platform:
      process-type: CONCURRENT  # Enable parallel execution
  llm:
    openai:
      api-key: ${WINDSURF_API_KEY}
      model: gpt-4
      temperature: 0.7
```
```

---

## 📊 Side-by-Side Comparison

| Feature | LangGraph (Python) | Embabel (Java) |
|---------|-------------------|----------------|
| **Type Safety** | ❌ Dictionary-based state | ✅ Compile-time checked |
| **Domain Modeling** | ❌ Strings everywhere | ✅ Rich domain objects |
| **Workflow Definition** | Manual (error-prone) | Automatic (inferred) |
| **Parallelization** | Limited | Built-in & automatic |
| **Testing** | Difficult | Easy (dependency injection) |
| **IDE Support** | Basic | Excellent (refactoring, etc.) |
| **Performance** | Python GIL limits | JVM multithreading |
| **Enterprise Integration** | Minimal | Full Spring ecosystem |
| **Learning Curve** | Shallow | Moderate |
| **Community** | Large | Growing |

## 🎯 When to Use Each

### Use LangGraph if:
- Rapid prototyping is priority
- Team is Python-centric
- Simple workflows without complex business logic
- Integration with Python ML libraries is essential

### Use Embabel if:
- Building production enterprise applications
- Type safety and compile-time checks are important
- Complex business logic and domain models
- Need Spring ecosystem (security, data, cloud)
- Performance and scalability matter
- Team is Java/JVM-centric

## 🚀 Getting Started

### LangGraph
```bash
cd langgraph-blog-titler
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
cp .env.example .env
# Add your WINDSURF_API_KEY to .env
python main.py
```

### Embabel
```bash
cd embabel-blog-titler
cp .env.example .env
# Add your WINDSURF_API_KEY to .env
mvn spring-boot:run
```

## 📚 Additional Resources

- [Embabel Documentation](https://github.com/embabel/embabel-agent)
- [LangGraph Documentation](https://langchain.com/langgraph)
- [Original Article](https://medium.com/@springrod/build-better-agents-in-java-vs-python-embabel-vs-langgraph-f7951a0d855c)

## 🤝 Contributing

Both projects are examples for learning. Feel free to:
- Extend with additional patterns from the article
- Add more comprehensive error handling
- Implement unit tests
- Add logging and monitoring

## 📄 License

MIT License - Use freely for learning and commercial projects.




