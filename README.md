# Movie Data Dashboard Website

<img src="" data-canonical-src="" style="display:block; margin-left: auto; margin-right: auto; width: 80%" alt="Trang chủ" />

## Giới thiệu / Introduction

Movie Data API là website cho phép quản lý dữ liệu phim trên cơ sở dữ liệu.

Movie Data API is a web API service that allows users to query and manage Movie database.

### Chức năng của ứng dụng / Functionality:
Gồm các API cho phép:
- Liệt kê dữ liệu danh sách phim, thể loại phim. Liệt kê dữ liệu danh sách phim theo thể loại
- Thêm, xóa, sửa phim, thể loại phim trong cơ sở dữ liệu
- Tìm kiếm phim theo tiêu đề và hiển thị kết quả phim. 

 <br/>

- List all movies and genres information. List movies by genres
- Add, delete, modify movies and genres database
- Search movie by title keywords

### API List:

- GET [/movies/api/v1/movies]
- GET [/movies/api/v1/movies/{id}]
- GET [/movies/api/v1/movies/search/title={keywordMovie}]
- POST [/movies/api/v1/movies/insert]
- PUT [/movies/api/v1/movies/{id}]
- DELETE [/movies/api/v1/movies/{id}]

<br/>

- GET [/movies/api/v1/genres]
- GET [/movies/api/v1/genres/{id}]
- GET [/movies/api/v1/genres/{id}/movies]
- POST [/movies/api/v1/genres/insert]
- PUT [/movies/api/v1/genres/{id}]
- DELETE [/movies/api/v1/genres/{id}]


### Công nghệ sử dụng / Tech stack:
- Programming Language: Java
- Database: PostgreSQL
- Backend API: Spring Boot Application (using Spring Data JPA)


## Demo
[Link API](https://movie-data-dashboard.onrender.com/movies/api/v1)

## Set up project locally
### Prerequisites
- Java 17
- PostgreSQL, pgAdmin 4
- (Optional) IntelliJ
- (Optional) Docker

### Installation

1. Clone the repo

2. Create file env in root folder
```
DATABASE_HOST=host.docker.internal
DATABASE_PORT=5432
DATABASE_NAME=MovieData
DATABASE_USERNAME=<your database username here>
DATABASE_PASSWORD=<your database password here>
```

2. Create database
- Connect pgAdmin to local PostgreDB Database. Create PostgreSQL Database `MovieData` on local.
- Restore database from file MovieData.sql

3. To run on Docker:
- Run `docker build -t docker_movie_data_api .` to build static files
- Run `docker run -p 8080:8080 --env-file .env docker_movie_data_api` to run web server at localhost:8080 

4. To run on IntelliJ:
- Run `./mvnw clean package -DskipTests` to build static files
- Run application on IntelliJ

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
