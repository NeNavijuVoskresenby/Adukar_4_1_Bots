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
import org.telegram.telegrambots.api.methods.send.*;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


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
            if (update.getCallbackQuery().getData().equals("Самые красивые места мира")){
                sendMsgWithButtons("ihugigiuyg", inlineButtons.keyboardMarkup("1", "2", "3"), chatIdFromCallBack);
                send
                /* sendMsg("1",chatIdFromCallBack);
                sendPhoto("https://vandruy.by/wp-content/uploads/2018/08/201302121832369554-min-1024x683.jpeg", chatIdFromCallBack);
          */  }


        }else {

        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().equals("/start")) {
            sendMsg("Начнём работать!", chatId);
            sendMsgWithButtons("Нажмите на кнопки ниже перечисленные", inlineButtons.keyboardMarkup("Самые красивые места мира"), chatId);
        }
        if (update.getMessage().getText().equals("Выход назад")) {
            sendMsg("Вы вернулись в первоначальное меню", chatId);
            sendMsgWithButtons("Выбирайте,что хотите узнать,увидеть", replyButtons.keyboardMarkup("Самые красивые места мира", "Кнопка 2"), chatId);
        }

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
            sendPhoto(sendPhotoRequest);
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