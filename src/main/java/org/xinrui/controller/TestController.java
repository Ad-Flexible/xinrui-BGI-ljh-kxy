package org.xinrui.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

/**
 * 测试用 Controller，演示常见 HTTP 请求处理
 */
@RestController // 等同于 @Controller + @ResponseBody（返回 JSON/字符串，不跳转页面）
@RequestMapping("/api/test") // 基础路径：所有接口以 /api/test 开头
public class TestController {

    /**
     * 1. 无参 GET 请求：返回欢迎消息
     * 访问路径：GET http://localhost:8080/api/test/hello
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot! This is TestController.";
    }

    /**
     * 2. 带路径参数的 GET 请求：根据 ID 查询信息
     * 访问路径：GET http://localhost:8080/api/test/user/123
     */
    @GetMapping("/user/{userId}")
    public Map<String, Object> getUserById(@PathVariable Long userId) { // @PathVariable 绑定路径参数
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "查询成功");

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("username", "testUser_" + userId);
        userData.put("age", 25 + (int)(userId % 10)); // 模拟年龄

        result.put("data", userData);
        return result; // Spring 自动转为 JSON 响应
    }

    /**
     * 3. 带请求参数的 GET 请求：分页查询
     * 访问路径：GET http://localhost:8080/api/test/users?page=1&size=10
     */
    @GetMapping("/users")
    public Map<String, Object> getUsers(
            @RequestParam(defaultValue = "1") int page, // 请求参数，默认值 1
            @RequestParam(defaultValue = "10") int size) { // 请求参数，默认值 10
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "分页查询成功");

        Map<String, Object> data = new HashMap<>();
        data.put("page", page);
        data.put("size", size);
        data.put("total", 100); // 模拟总条数
        data.put("list", java.util.Arrays.asList("user1", "user2", "user3")); // 模拟列表数据
        result.put("data", data);

        return result;
    }

    /**
     * 4. POST 请求：接收 JSON 请求体，返回处理结果
     * 访问路径：POST http://localhost:8080/api/test/create
     * 请求体示例：{"name":"张三","age":28,"email":"zhangsan@example.com"}
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) { // @RequestBody 绑定 JSON 请求体
        // 模拟业务逻辑：保存用户信息（实际项目中可调用 Service 层）
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "用户创建成功");

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", System.currentTimeMillis()); // 模拟生成的用户 ID
        userData.put("name", user.getName());
        userData.put("age", user.getAge());
        userData.put("email", user.getEmail());
        result.put("data", userData);

        // 返回 ResponseEntity，可自定义状态码（默认 200 OK）
        return new ResponseEntity<>(result, HttpStatus.CREATED); // 201 Created
    }

    /**
     * 5. PUT 请求：更新用户信息（全量更新）
     * 访问路径：PUT http://localhost:8080/api/test/update/123
     * 请求体示例：{"name":"李四","age":30,"email":"lisi@example.com"}
     */
    @PutMapping("/update/{userId}")
    public Map<String, Object> updateUser(
            @PathVariable Long userId,
            @RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "用户更新成功");

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("name", user.getName());
        userData.put("age", user.getAge());
        userData.put("email", user.getEmail());
        userData.put("updatedAt", System.currentTimeMillis()); // 模拟更新时间
        result.put("data", userData);

        return result;
    }

    /**
     * 6. DELETE 请求：删除用户
     * 访问路径：DELETE http://localhost:8080/api/test/delete/123
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        // 模拟删除逻辑（实际项目中调用 Service 层）
        System.out.println("删除用户：" + userId);
        return ResponseEntity.noContent().build(); // 204 No Content（无响应体）
    }


    // ------------------------------ 内部辅助类（模拟请求/响应实体） ------------------------------
    /**
     * 用户实体类（用于接收 POST/PUT 请求的 JSON 数据）
     */
    static class User {
        private String name;
        private Integer age;
        private String email;

        // 必须提供 getter/setter（Spring 需要通过反射赋值）
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}


