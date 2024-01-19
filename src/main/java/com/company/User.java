package com.company;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private Long chatId;

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }

    private BotState botState = BotState.START;
}
