package br.com.fernanda.todoapp.model.enums;

public enum StatusTarefa {
    FAZER, FAZENDO, FEITO;

    public static StatusTarefa convert(String status) {

        if (status == null) {
            return null;
        }

        if (status.equalsIgnoreCase("todo")) {
            return FAZER;
        }

        if (status.equalsIgnoreCase("doing")) {
            return FAZENDO;
        }

        if (status.equalsIgnoreCase("done")) {
            return FEITO;
        }

        return FAZER;
    }
}
