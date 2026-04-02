package org.volkov.hellodb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.volkov.hellodb.dao.Dao;
import org.volkov.hellodb.dao.UserDao;
import org.volkov.hellodb.model.UserEntity;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Scanner;

public class Main {
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
        Dao<UserEntity> userDao = new UserDao();

        boolean exit_flag = false;
        while (!exit_flag) {
            System.out.print(PRINT_ACTIONS);
            System.out.print("Ваш выбор: ");
            int action = in.nextInt();

            switch (action) {
                case 0 -> { exit_flag = true; }
                case 1 -> createUser(userDao, in);
                case 2 -> getUser(userDao, in);
                case 3 -> updateUser(userDao, in);
                case 4 -> deleteUser(userDao, in);
                case 5 -> userDao.getAll().forEach(System.out::println);
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

    private static void createUser(Dao<UserEntity> userDao, Scanner in) {
        in.nextLine();
        System.out.print("Введите данные пользователя: ");

        String input = in.nextLine();
        Optional<UserEntity> opt = parse(input);
        opt.ifPresent(userDao::create);
    }

    private static Optional<UserEntity> getUser(Dao<UserEntity> userDao, Scanner in) {
        in.nextLine();
        System.out.print("Введите id: ");

        long id = in.nextLong();
        Optional<UserEntity> opt = userDao.get(id);
        if (opt.isEmpty())
            System.out.println("Запись не найдена");
        else
            System.out.println(opt.get());

        return opt;
    }

    private static void updateUser(Dao<UserEntity> userDao, Scanner in){
        Optional<UserEntity> opt = getUser(userDao, in);
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

        userDao.update(newUser);
    }

    private static void deleteUser(Dao<UserEntity> userDao, Scanner in){
        Optional<UserEntity> opt = getUser(userDao, in);
        if (opt.isEmpty())
            return;

        userDao.delete(opt.get());
    }
}
