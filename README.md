<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
[![LinkedIn][linkedin-shield]][linkedin-url]
[![Leetcode][leetcode-shield]][leetcode-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Sushi Shop Server</h3>

  <p align="center">
    A simulated sushi shop server-side program that takes orders from the customers, processes the orders in parallel, shows and updates the order status.
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
  </ol>
</details>

### Built With

* [![spring-boot][spring-boot]][spring-boot-url]
* [![maven][maven]][maven-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

* JDK 17 (https://www.oracle.com/java/technologies/downloads/#java17)
* Maven
  ```sh
  brew install maven
  ```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/joenghy/sushi_shop_server.git
   ```
2. Change directory
   ```sh
   cd sushi_shop_server
   ```
3. Run Spring Boot App
   ```sh
   mvn spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

You may use tools like Postman or curl to test the REST APIs. Please not that the server is started on port 9000.
The following APIs are supported:

- POST /api/orders
- DELETE /api/orders/{order_id}
- GET /api/orders/status
- PUT /api/orders/{order_id}/pause
- PUT /api/orders/{order_id}/resume

I am working towards a detailed documentation on the APIs. Please get back later.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Implement mandatory APIs
- [x] Implement bonus APIs
- [x] Implement basic test cases
- [ ] Add API documentation
- [ ] Improve test coverage

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/joe-ng-6098071b0
[leetcode-shield]: https://img.shields.io/badge/-Leetcode-black.svg?style=for-the-badge&logo=leetcode&colorB=555
[leetcode-url]: https://leetcode.com/joe_ng_ca/
[spring-boot]: https://img.shields.io/badge/spring-boot?style=for-the-badge&logo=spring-boot&logoColor=white
[spring-boot-url]: https://spring.io/projects/spring-boot
[maven]: https://img.shields.io/badge/maven-green?style=for-the-badge&colorB=555
[maven-url]: https://maven.apache.org
