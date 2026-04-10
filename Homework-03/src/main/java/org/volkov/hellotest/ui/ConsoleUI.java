package org.volkov.hellotest.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.volkov.hellotest.dao.UserDao;
import org.volkov.hellotest.entity.UserEntity;
import org.volkov.hellotest.service.UserService;
import org.volkov.hellotest.service.UserServiceImpl;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PRINT_ACTIONS = """
            Выберите действие:
            0. Выход
            1. Создать запись
            2. Найти запись по Id
            3. Изменить запись
            4. Удалить запись
            5. Вывести все записи""";

    static void main() {
        Scanner in = new Scanner(System.in);
        UserService userService = new UserServiceImpl(new UserDao());

        boolean exit_flag = false;
        while (!exit_flag) {
            System.out.print(PRINT_ACTIONS);
            System.out.print("Ваш выбор: ");
            int action = in.nextInt();

            switch (action) {
                case 0 -> { exit_flag = true; }
                case 1 -> createUser(userService, in);
                case 2 -> getUser(userService, in);
                case 3 -> updateUser(userService, in);
                case 4 -> deleteUser(userService, in);
                case 5 -> userService.getAllUsers().forEach(System.out::println);
                default -> System.out.println();
            }
        }
    }

    private static Optional<UserEntity> parse(String input) {
        String[] tokens = input.split(";");
        if (tokens.length != 3) {
            LOGGER.warn("Ошибка при разборе строки: {}", input);
            return Optional.empty();
        }

        for (String t: tokens) {
            t = t.trim();
        }
        int age;
        try {
            age = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            LOGGER.warn("Ошибка при разборе строки.", e);
            age = 0;
        }

        UserEntity user = new UserEntity();
        user.setName(tokens[0]);
        user.setAge(age);
        user.setEmail(tokens[2]);
        user.setCreated_at(ZonedDateTime.now().toOffsetDateTime());

        return Optional.of(user);
    }

    private static void createUser(UserService userService, Scanner in) {
        in.nextLine();
        System.out.print("Введите данные пользователя: ");

        String input = in.nextLine();
        Optional<UserEntity> opt = parse(input);
        opt.ifPresent(userService::createUser);
    }

    private static Optional<UserEntity> getUser(UserService userService, Scanner in) {
        in.nextLine();
        System.out.print("Введите id: ");

        long id = in.nextLong();
        Optional<UserEntity> opt = userService.getUserById(id);
        if (opt.isEmpty())
            System.out.println("Запись не найдена");
        else
            System.out.println(opt.get());

        return opt;
    }

    private static void updateUser(UserService userService, Scanner in){
        Optional<UserEntity> opt = getUser(userService, in);
        if (opt.isEmpty())
            return;

        in.nextLine();
        Optional<UserEntity> newOpt = parse(in.nextLine());
        if (newOpt.isEmpty())
            return;

        UserEntity user = opt.get();
        UserEntity newUser = newOpt.get();

        newUser.setId(user.getId());
        newUser.setCreated_at(user.getCreated_at());

        userService.updateUser(newUser);
    }

    private static void deleteUser(UserService userService, Scanner in){
        Optional<UserEntity> opt = getUser(userService, in);
        if (opt.isEmpty())
            return;

        userService.deleteUser(opt.get());
    }
}
