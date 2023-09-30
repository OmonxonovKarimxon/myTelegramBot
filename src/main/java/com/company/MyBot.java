package com.company;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "samarqand_umra_bot";
    }

    @Override
    public String getBotToken() {
        return "5764855582:AAHYaty06-la8O_KUKA5M6M7hueIZQs03k4";
    }
    String groupNumber;
    String ziyoratTuri;
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");
        System.out.println("hello horld djfv dfbvdfb");

        long chatId = BotService.getChatId(update);
        User user = BotService.getUserFromListByChatId(chatId);

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                String text = update.getMessage().getText();
                if (text.equals("/start")||user.getBotState().equals(BotState.START)) {
                    execute(BotService.start(update));
                } else if (user.getBotState().equals(BotState.TRIP_TYPE)) {
                    ziyoratTuri = text;
                    execute(BotService.ziyoratTuri(update));
                } else if (user.getBotState().equals(BotState.GROUP_NUMBER)) {
                    int ka = Integer.parseInt(text);
                    groupNumber = text;
                    execute(BotService.groupNumber(update));
                }

            }
            if (update.getMessage().hasDocument()) {
                String doc_id = update.getMessage().getDocument().getFileId();
                String doc_name = update.getMessage().getDocument().getFileName();
                String doc_mine = update.getMessage().getDocument().getMimeType();
                Long doc_size = update.getMessage().getDocument().getFileSize();


                String filePath ="./src/main/resources/newFile.xlsx" ;

                Document document = new Document();
                document.setMimeType(doc_mine);
                document.setFileName(doc_name);
                document.setFileSize(doc_size);
                document.setFileId(doc_id);

                GetFile getFile = new GetFile();
                getFile.setFileId(document.getFileId());
                try {
                    org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
                    downloadFile(file, new File(filePath));
                    Map<Integer, List<String>> rowsList = BotService.readExcelFile(filePath);

                    for (int i = 0; i < rowsList.size(); i++) {
                        List<String> row = rowsList.get(i);
                        String imagePath = BotService.sendFile(row, ziyoratTuri, groupNumber, chatId);
                        SendDocument sendDocumentRequest = new SendDocument();
                        sendDocumentRequest.setChatId(chatId);
                        sendDocumentRequest.setDocument(new InputFile(new File(imagePath)));
                       execute(sendDocumentRequest);
                        Files.delete(Path.of(imagePath));

                    }
                        BotService.changeState(update);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }



    }
}
