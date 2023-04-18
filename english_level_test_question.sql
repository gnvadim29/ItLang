-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Час створення: Лют 26 2023 р., 00:12
-- Версія сервера: 10.1.48-MariaDB
-- Версія PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `ItLang`
--

-- --------------------------------------------------------

--
-- Структура таблиці `english_level_test_question`
--

CREATE TABLE `english_level_test_question` (
  `id` bigint(20) NOT NULL,
  `question_answer1` varchar(255) DEFAULT NULL,
  `question_answer2` varchar(255) DEFAULT NULL,
  `question_answer3` varchar(255) DEFAULT NULL,
  `question_answer4` varchar(255) DEFAULT NULL,
  `question_correct_answer` int(11) NOT NULL,
  `question_level` varchar(255) DEFAULT NULL,
  `question_title` text,
  `user_answer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=cp1251;

--
-- Дамп даних таблиці `english_level_test_question`
--

INSERT INTO `english_level_test_question` (`id`, `question_answer1`, `question_answer2`, `question_answer3`, `question_answer4`, `question_correct_answer`, `question_level`, `question_title`, `user_answer`) VALUES
(1, 'My name is John.', 'I\'m fine, thanks.', 'I am 20 years old.', '', 1, 'A1', 'What is your name?', -1),
(2, 'I am from Spain.', ' I am 25 years old.', 'I am fine, thank you.', '', 3, 'A1', 'How are you?', -1),
(3, 'I am a student.', 'I am from New York.', 'I like swimming.', '', 2, 'A1', 'Where are you from?', -1),
(4, 'My favorite color is green.', ' I am 18 years old.', ' I like to play football.', '', 1, 'A1', 'What is your favorite color?', -1),
(5, 'Yes, I do.', 'My name is Tom.', 'I have a dog.', '', 1, 'A1', 'Do you speak English?', -1),
(6, 'It is sunny outside.', 'I like to watch movies.', ' It is 3 o\'clock.', '', 3, 'A1', 'What time is it?', -1),
(7, ' I am 20 years old.', ' I like to dance.', ' My favorite color is red.', '', 1, 'A1', 'How old are you?', -1),
(8, 'I am a teacher.', 'I like to play the guitar.', 'My favorite sport is basketball.', '', 1, 'A1', 'What do you do?', -1),
(9, 'I am 30 years old.', 'My favorite hobby is painting.', ' I live in Paris.', '', 3, 'A1', 'Where do you live?', -1),
(10, 'I have a fish.', 'My favorite food is sushi.', 'I am a student.', '', 1, 'A1', 'What is your favorite food?', -1),
(11, 'I like rock music.', 'I am from London.', 'My favorite food is pizza.', '', 1, 'A2', 'What is your favorite type of music?', -1),
(12, 'I enjoy playing sports.', 'My favorite color is blue.', 'I have two dogs.', '', 1, 'A2', 'What do you like to do in your free time?', -1),
(13, 'I am a teacher.', 'My favorite hobby is photography.', 'I have one brother.', '', 1, 'A2', 'What is your job?', -1),
(14, 'I like to watch movies.', ' My favorite food is spaghetti.', 'It is sunny.', '', 3, 'A2', 'What is the weather like today?', -1),
(15, ' No, I haven\'t.', 'Yes, I have.', 'I am from Brazil.', '', 2, 'A2', 'Have you traveled abroad before?', -1),
(16, 'My favorite color is green.', 'I have three sisters.', 'I went to the beach.', '', 3, 'A2', 'What did you do last weekend?', -1),
(17, 'I am a student.', 'My favorite movie is Titanic.', 'I like to play video games.', '', 2, 'A2', 'What is your favorite movie?', -1),
(18, 'I am 25 years old.', 'I have a cat.', 'My favorite season is summer.', '', 3, 'A2', 'What is your favorite season?', -1),
(19, 'My favorite hobby is cooking.', ' Yes, I have one sister.', 'I like to play video games.', '', 2, 'A2', 'Do you have any siblings?', -1),
(20, ' I like to listen to music.', 'My favorite book is Harry Potter.', ' I have a hamster.', '', 2, 'A2', 'What is your favorite book?', -1),
(21, 'Me and my friend went to the movies.', 'My friend and I went to the movies.', 'I and my friend went to the movies.', 'My friend and me went to the movies.', 2, 'B1', 'Which sentence is grammatically correct?', -1),
(22, 'The teacher graded the papers.', 'The papers were graded by the teacher.', 'The student wrote the essay.', 'The essay was written by the student.', 2, 'B1', 'Which sentence is in the passive voice?', -1),
(23, 'to eat', 'to have eaten', 'to eating', 'eating', 3, 'B1', 'I was looking forward ...... at the new restaurant, but it was closed.', -1),
(24, 'Whatever', 'No matter how', 'However much', 'Although', 2, 'B1', '...... tired Melissa is when she gets home from work, she always makes time to say goodnight to the children.', -1),
(25, 'booked', 'ordered', 'commanded', 'asked', 2, 'B1', 'The shop didn\'t have the shoes I wanted, but they\'ve ...... a pair specially for me.', -1),
(26, 'thinking', 'round', 'planned', 'about', 4, 'B2', 'Have you got time to discuss your work now or are you ...... to leave?', -1),
(27, 'quite', 'beyond', 'already', 'almost', 4, 'B2', 'She came to live here ...... a month ago.', -1),
(28, 'undress', 'unfasten', 'unlock', 'untie', 2, 'B2', 'Once the plane is in the air, you can ...... your seat belts if you wish.', -1),
(29, 'place', 'position', 'opportunity', 'possibility', 4, 'B2', 'I left my last job because I had no ...... to travel.', -1),
(30, 'little', 'small', 'light', 'mere', 1, 'B2', 'It wasn\'t a bad crash and ...... damage was done to my car.', -1),
(31, 'would explain', 'explained', 'to explain', 'will explain', 2, 'C', 'I\'d rather you ...... to her why we can\'t go.', -1),
(32, 'sides', 'features', 'perspectives', 'shades', 3, 'C', 'Before making a decision, the leader considered all ...... of the argument.', -1),
(33, 'greatly', 'highly', 'strongly', 'readily', 2, 'C', 'This new printer is recommended as being ...... reliable.', -1),
(34, 'retrace', 'regress', 'resume', 'return', 1, 'C', 'When I realised I had dropped my gloves, I decided to ...... my steps.', -1),
(35, 'region', 'quarter', 'vicinity', 'district', 3, 'C', 'Anne\'s house is somewhere in the ...... of the railway station.', -1);

--
-- Індекси збережених таблиць
--

--
-- Індекси таблиці `english_level_test_question`
--
ALTER TABLE `english_level_test_question`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
