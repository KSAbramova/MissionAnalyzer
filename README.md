# 🗡️ Mission Archive

**Архив миссий Токийского магического колледжа**

Веб-приложение для хранения, анализа и генерации отчётов по магическим миссиям.

![Java](https://img.shields.io/badge/Java-25%2B-007396?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3+-6DB33F?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge)

---

## ✨ Возможности

- **Загрузка миссий** в различных форматах: JSON, YAML, XML, TXT
- **Хранение** всех миссий в PostgreSQL
- **Просмотр** детальной информации о миссии
- **Генерация отчётов**:
  - Базовый отчёт
  - Полный детальный отчёт (со всеми секциями)
- **Фильтрация** по исходу, локации и диапазону дат
- **Удаление** миссий
- **REST API** + Swagger документация
- **Современный веб-интерфейс** на Bootstrap + Thymeleaf

---

## 🛠 Технологии

- **Backend**: Spring Boot 3, Spring Data JPA, Spring Web
- **База данных**: PostgreSQL
- **Шаблонизатор**: Thymeleaf
- **Маппинг**: Ручной Mapper (MissionMapper)
- **Парсеры**: Chain of Responsibility (JSON, YAML, XML, TXT)
- **Документация API**: OpenAPI (Swagger)
- **Frontend**: Bootstrap 5
