package com.example.currencyparser.dto;

import lombok.Data;
import java.util.List;

@Data
public class ParserRequest {
    private List<String> sources;
    private Boolean immediateSave;
    private List<String> targetCurrencies;

    // Конструкторы
    public ParserRequest() {}

    public ParserRequest(List<String> sources, Boolean immediateSave, List<String> targetCurrencies) {
        this.sources = sources;
        this.immediateSave = immediateSave != null ? immediateSave : true;
        this.targetCurrencies = targetCurrencies;
    }

    // Геттеры и сеттеры (Lombok @Data должен генерировать их, но на всякий случай)
    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public Boolean getImmediateSave() {
        return immediateSave;
    }

    public void setImmediateSave(Boolean immediateSave) {
        this.immediateSave = immediateSave;
    }

    public List<String> getTargetCurrencies() {
        return targetCurrencies;
    }

    public void setTargetCurrencies(List<String> targetCurrencies) {
        this.targetCurrencies = targetCurrencies;
    }
}