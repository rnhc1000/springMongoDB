## _Reactive RESTFul API - MongoDB_ <br />

NoSQL databases represent a new challenge when compared with relational databases.
Combined with Reactive Programming Paradigm implemented by Spring Webflux fully supporting
<a href="https://www.reactive-streams.org/" target= "_blanket">Reactive Streams</a> is a game changer
on how to take advantage of using asynchronous stream processing with non-blocking back pressure, offering
significant gains of performance for those applications that must support thousands of simultaneous requests.

## _Table of contents_

- [_Overview_](#overview)
- [_Requirements_](#requirements)
- [_Project Structure_](#requirements)
- [_Howto Build and Run_](#requirements)
- [_Screenshot_](#screenshot)
- [_Links_](...)
- [_Built with_](#built-with)
- [_Code Snippet_](#requirements)
- [_Continued development_](#continued-development)
- [_Useful resources_](#useful-resources)
- [_Author_](#requirements)
- [_Portfolio_](#requirements)

## _Overview_

This API has a bunch of endpoints to return Posts and Comments associated to a User according to this the JSON representation:

```
{
    _id: ObjectId('67b0edf01a432d53fce1d509'),
    moment: ISODate('2025-02-13T14:50:00.000Z'),
    title: 'Business Trip',
    body: 'Travel to Sampa',
    author: {
        name: 'Maria Brown'
    },
    comments: [
        {
            text: 'Have a nice and safe trip',
            moment: ISODate('2025-02-13T14:55:00.000Z'),
            author: {
                name: 'Alex Green'
            }
    },
        {
            text: 'Enjoy it!',
            moment: ISODate('2025-02-13T14:59:00.000Z'),
            author: {
                name: 'Bob Grey'
            }
        }
    ],
    _class: 'br.dev.ferreiras.mongodb.models.entities.Post'
}
```

<br />

## _Features_

The app has been coded using Java 21, Spring Boot 3.4.2, MongoDB 4.4.2, Maven, Javadoc, Reactive Spring MongoDB, Spring Webflux,
Docker.
<br />

## _Project Structure_

- docs
    - javadocs
- src
    - main
    - java
        - br.dev.ferreiras.mongodb
            - config
            - controller
                - handlers
            - dto
            - model
                - entities
                - dto
                - embedded
            - mapper
            - repository
            - services
                - exceptions
    - resources
        - application-test.properties
        - application-properties
    - test
-

## _Howto Build and Run_

  ```
  - MongoDB Database : http://127.0.0.1:27017
  - profile active: test
  - service socket: 127.0.0.1:8080
  - tweak a few knobs to get it up and running
   ```

## _Screenshot_

[![](./mermaid-diagram-2025-02-16-112241.svg)]()

## _Links_

- Live Site URL: <a href="https://api.ferreiras.dev.br/swagger-ui/index.html" target="_blank">API MongoDB</a>

## _Built with_

[![My Skills](https://skillicons.dev/icons?i=java,spring,mongodb,maven,docker,redhat,idea,git,github,)](https://skillicons.dev)

## _Code Snippet_

```java

/**
 *
 * @author ricardo@ferreiras.dev.br
 * @version 1.1.030901
 * @since 1.0
 *
 */
@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {


  @Query("""   
      {
      'title': { $regex: ?0, $options: 'i' }
      }
      """)
  Flux<Post> searchByTitle(String text);

  @Query(
      """
          {
            $and: [
              { 'moment': { $gte: ?1 } }, { 'moment': { $lte: ?2 } }, {
                $or: [
                  {'title': { $regex: ?0, $options: 'i' } },
                  {'body':  { $regex: ?0, $options: 'i' } },
                  {'comments.text': { $regex: ?0, $options: 'i' } }
                ]
              }
            ]
          }
          """)
  Flux<Post> fullSearch(String text, Instant startMoment, Instant endMoment);

  Flux<Post> findByTitleContainingIgnoreCase(String text);
}

``` 

## _Continued development_

- Unit Tests
- HATEOAS
- Versioning
- Swagger Docs

### _Useful resources_

- [https://spring.io/] Awesome Java framework!.
- [https://start.spring.io/]  Handy startup tool.
- [https://mvnrepository.com/] Tools that help tackle the beast
- [https://docs.spring.io/spring-data/mongodb/reference/]  MongoDB reference doc
- [https://www.reactive-streams.org/]  Reactive Streams
- [https://docs.spring.io/spring-framework/reference/web/webflux.html/]  Spring Webflux

## _Author_

<a href="mailto:ricardo@ferreiras.dev.br">Ricardo Ferreira</a>

## - _Portfolio_

<a href="https://www.ferreiras.dev.br" target="_blank">My Portfolio...</a>

