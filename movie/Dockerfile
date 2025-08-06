# ---- Build Stage ----
#  Sử dụng image Gradle với JDK 21 để build ứng dụng. Đặt tên stage là build.
FROM gradle:8.7.0-jdk21 AS build
# Đặt thư mục làm việc trong container là /app.
WORKDIR /app
# Copy toàn bộ mã nguồn từ máy host vào thư mục /app trong container.
COPY . .
# Chạy lệnh Gradle để build file jar của ứng dụng Spring Boot.
RUN gradle clean bootJar --no-daemon

# ---- Run Stage ----
# Sử dụng image Amazon Corretto JDK 21 để chạy ứng dụng.
FROM amazoncorretto:21.0.8
# Đặt lại thư mục làm việc là /app cho stage chạy.
WORKDIR /app
# Copy file jar đã build từ stage build sang stage chạy, đổi tên thành app.jar.
COPY --from=build /app/build/libs/*.jar app.jar
# Mở port 8080 để ứng dụng lắng nghe (thường là port của Spring Boot).
EXPOSE 8080
# Thiết lập lệnh khởi chạy ứng dụng khi container start.
ENTRYPOINT ["java","-jar","app.jar"]