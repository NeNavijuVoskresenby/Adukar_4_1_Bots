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

            if (update.getCallbackQuery().getData().equals("Самые красивые места мира")) {
                sendMsgWithButtons("Выбирайте место", inlineButtons.keyboardMarkup3("Пекин" , "Дубай" , "Лондон"), chatIdFromCallBack);
                }
            if (update.getCallbackQuery().getData().equals("Дубай")) {
                sendMsg("Дубай", chatIdFromCallBack);
                sendPhoto("https://q-xx.bstatic.com/xdata/images/hotel/max500/185219896.jpg?k=3d4cfc7f2a2e62fb2612b2973d1146bab1c3f40a348894b1a8c04a6ec5946d01&o=", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("25.0757595", "54.9475542", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Лондон")) {
                sendMsg("Лондон", chatIdFromCallBack);
                sendPhoto("https://cdn24.img.ria.ru/images/155635/45/1556354505_0:438:2000:1563_1920x0_80_0_0_64fb6d242c6240d9e44e5de1a31a7d6f.jpg", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("51.5285582", "-0.2416812", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Пекин")) {
                sendMsg("Пекин", chatIdFromCallBack);
                sendPhoto("https://vandruy.by/wp-content/uploads/2018/08/201302121832369554-min-1024x683.jpeg", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("53.892288", "27.5760783", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Красивые постройки в Минске")) {
                sendMsgWithButtons("Выбирайте место", inlineButtons.keyboardMarkup3("Костел Святых Сымона и Елены" , "Костел Девы Марии" , "Костел святого Роха"), chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Костел Святых Сымона и Елены")) {
                sendMsg("Костел Святых Сымона и Елены", chatIdFromCallBack);
                sendPhoto("https://34travel.me/media/upload/images/2017/september/churches/new/IMG_8596.jpg", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("53.8965271", "27.5453701", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Костел Девы Марии")) {
                sendMsg("Костел Девы Марии", chatIdFromCallBack);
                sendPhoto("https://34travel.me/media/upload/images/2018/november/34dstpr/marii1.jpg", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("53.9031756", "27.552394", chatIdFromCallBack);
            }
            if (update.getCallbackQuery().getData().equals("Костел святого Роха")) {
                sendMsg("Костел святого Роха", chatIdFromCallBack);
                sendPhoto("https://34travel.me/media/upload/images/2018/november/34dstpr/IMG_0594.jpg", chatIdFromCallBack);
                sendPoll(chatIdFromCallBack);
                sendLocation("53.9109788,", "27.5780278", chatIdFromCallBack);
            }






        } else {
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("Кости")) {
                sendDice(chatId);
            }
            if (update.getMessage().getText().equals("/start")) {
                sendMsg("Начнём работать!", chatId);
                sendMsgWithButtons("Нажмите на кнопки ниже перечисленные", inlineButtons.keyboardMarkup("Самые красивые места мира"), chatId);
                sendMsgWithButtons(".", inlineButtons.keyboardMarkup("Красивые постройки в Минске" ), chatId);
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
        sendPoll.setQuestion("Понравилась ли вам данная картинка?");
        sendPoll.setAnonymous(true);
        sendPoll.setOptions(List.of("Да", "Нет"));
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

    /*public synchronized void sendContact(Long chatId) {
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

    public synchronized void sendLocation(String lat, String lon, long chatId) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(chatId);
        sendLocation.setLatitude(Float.valueOf(lat));
        sendLocation.setLongitude(Float.valueOf(lon));

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