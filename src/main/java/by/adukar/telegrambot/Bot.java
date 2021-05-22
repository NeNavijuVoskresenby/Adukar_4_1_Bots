package by.adukar.telegrambot;

import by.adukar.telegrambot.buttons.inline.InlineButtons;
import by.adukar.telegrambot.buttons.reply.ReplyButtons;
import by.adukar.telegrambot.consts.Commands;
import by.adukar.telegrambot.consts.Paths;
import by.adukar.telegrambot.consts.Photos;
import by.adukar.telegrambot.consts.Text;
import by.adukar.telegrambot.enums.Color;
import by.adukar.telegrambot.service.FileService;
import by.adukar.telegrambot.service.TextService;
import by.adukar.telegrambot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


public class Bot extends TelegramLongPollingBot {

    ReplyButtons replyButtons = new ReplyButtons();

    UserService userService = new UserService();
    TextService textService = new TextService();
    FileService fileService = new FileService();
    InlineButtons inlineButtons = new InlineButtons();

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        sendAnswerFromBot(update);
    }

    @SneakyThrows
    public void sendAnswerFromBot(Update update) {
        if (update.hasCallbackQuery()) {
            Long chatIdFromCallBack = update.getCallbackQuery().getFrom().getId().longValue();
            if (update.getCallbackQuery().getData().equals("Текст для кнопки")) {
                sendMsg("Обработка инлайн кнопки", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Пекин")) {
                sendMsg("Пекин", chatIdFromCallBack);
               sendPhoto("https://vandruy.by/wp-content/uploads/2018/08/201302121832369554-min-1024x683.jpeg", chatIdFromCallBack);
                ;
            }


            if (update.getCallbackQuery().getData().equals("Самые красивые места мира")) {
                sendMsgWithButtons("Выбирайте место", inlineButtons.keyboardMarkup("Пекин" , "Дубай" , "Лондон"), chatIdFromCallBack);
                }
            if (update.getCallbackQuery().getData().equals("Дубай")) {
                sendMsg("Дубай", chatIdFromCallBack);
                sendPhoto("https://q-xx.bstatic.com/xdata/images/hotel/max500/185219896.jpg?k=3d4cfc7f2a2e62fb2612b2973d1146bab1c3f40a348894b1a8c04a6ec5946d01&o=", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Лондон")) {
                sendMsg("Лондон", chatIdFromCallBack);
                sendPhoto("https://cdn24.img.ria.ru/images/155635/45/1556354505_0:438:2000:1563_1920x0_80_0_0_64fb6d242c6240d9e44e5de1a31a7d6f.jpg", chatIdFromCallBack);
            }

        } else {

            Long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("Голосование")) {
                sendPoll(chatId);
            }
            if (update.getMessage().getText().equals("Кубик")) {
                sendDice(chatId);
            }
            if (update.getMessage().getText().equals("/start")) {
                sendMsg("Начнём работать!", chatId);
                sendMsgWithButtons("Нажмите на кнопки ниже перечисленные", inlineButtons.keyboardMarkup("Самые красивые места мира"), chatId);
            }
            if (update.getMessage().getText().equals("Выход назад")) {
                sendMsg("Вы вернулись в первоначальное меню", chatId);
                sendMsgWithButtons("Выбирайте,что хотите узнать,увидеть", replyButtons.keyboardMarkup("Самые красивые места мира","Кнопка 2"), chatId);
            }

        }
    }

    @SneakyThrows
    public synchronized void sendPoll(long chatId) {
        SendPoll sendPoll = new SendPoll();
        sendPoll.enableNotification();
        sendPoll.setQuestion("Какая картинка вам понравилась во время просмотра? :)");
        sendPoll.setAnonymous(true);
        sendPoll.setOptions(List.of("Пекин", "Дубай", "Лондон"));
        sendPoll.setChatId(chatId);
        sendPoll.setCorrectOptionId(2);
        execute(sendPoll);
    }
    @SneakyThrows
    public synchronized void sendDice(long chatId){
        SendDice sendDice = new SendDice();
        sendDice.setChatId(chatId);
        try {
            execute(sendDice);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public synchronized void sendMsg(String message, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    public synchronized void sendContact(Long chatId) {
        SendContact sendContact = new SendContact();
        sendContact.setPhoneNumber("+375447357152");
        sendContact.setFirstName("Anton");
        sendContact.setLastName("Kupreichik");
        sendContact.setChatId(chatId);
        try {
            execute(sendContact);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

  /*  public synchronized void sendDocument(Long chatId) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument("http://www.africau.edu/images/default/sample.pdf");
        sendDocument.setCaption("Текст к документу");
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            System.out.println( "Exception: " + e.toString());
        }
    }*/

    public synchronized void sendLocation(Long chatId) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(chatId);
        sendLocation.setLatitude(Float.valueOf("-33.830693"));
        sendLocation.setLongitude(Float.valueOf("151.219"));

        try {
            execute(sendLocation);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    public synchronized void sendPhoto(String pathToPhoto, Long chatId) {
        SendPhoto sendPhotoRequest = new SendPhoto();
        sendPhotoRequest.setChatId(chatId);
        sendPhotoRequest.setPhoto(pathToPhoto);
        try {
            execute(sendPhotoRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendMsgWithButtons(String message, ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }

    public synchronized void sendMsgWithButtons(String message, InlineKeyboardMarkup replyKeyboardMarkup, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }


    @Override
    public String getBotUsername() {
        return "OperatorAdukar_bot";
    }

    @Override
    public String getBotToken() {
        return "1861362477:AAFEo6uq-0HTw6rMAJyP0dHz_4silJDWxIM";
    }
}