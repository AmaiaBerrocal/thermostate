package com.thermostate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.*;

class Item {
    int numberOfItems;
    String description;
    int amountPerUnit;
    int discount;

    public Item(int numberOfItems, String description, int amountPerUnit, int discount) {
        this.numberOfItems = numberOfItems;
        this.description = description;
        this.amountPerUnit = amountPerUnit;
        this.discount = discount;
    }
}

public class SupermarketReceiptParser {
    public static List<Item> parseReceipt(List<String> fileLines) {
        List<Item> items = new ArrayList<>();
        Map<String, Integer> descuentosGenerales = new HashMap<>();
        boolean parsingProducts = false;
        boolean parsingDiscounts = false;

        for (String line : fileLines) {
            System.out.println(line);
            line = line.trim();

            if (line.startsWith("DESCUENTOS POR OFERTA")) {
                parsingProducts = false;
                parsingDiscounts = true;
                continue;
            }

            if (line.contains("€/TOT")) { // Detectamos el encabezado de productos
                parsingProducts = true;
                continue;
            }

            if (parsingProducts) {
                Matcher m = Pattern.compile("(\\d+\\t)?\\s*([A-ZÁÉÍÓÚÑ./\\s]+)\\s*(\\d+\\.\\d+)?\\s*(\\d+\\.\\d+)\\s*(-?\\d+\\.\\d+)?").matcher(line);
                if (m.find()) {
                    String producto = m.group(2).trim();
                    int unidades = 1;
                    if (line.matches("^\\d+\\s{2,}.*")) {
                        unidades = Integer.valueOf(line.substring(0, line.indexOf(" ")));
                    }
                    int precioTotal = unidades > 1 ? (int) (Double.parseDouble(m.group(4)) * 100) : (int) (Double.parseDouble(m.group(3)) * 100);
                    int descuento = m.group(5) != null ? (int) (Double.parseDouble(m.group(5)) * 100) : 0;
                    int precioPorUnidad = unidades > 1 && m.group(3) != null ? (int) (Double.parseDouble(m.group(3)) * 100) : precioTotal;

                    items.add(new Item(unidades, producto, precioPorUnidad, descuento));
                }
            }

            if (parsingDiscounts) {
                Matcher d = Pattern.compile("([A-ZÁÉÍÓÚÑ./\\s]+)\\s*(-?\\d+\\.\\d+)").matcher(line);
                if (d.find()) {
                    String producto = d.group(1).trim();
                    int descuento = (int) (Double.parseDouble(d.group(2)) * 100);
                    descuentosGenerales.put(producto, descuentosGenerales.getOrDefault(producto, 0) - descuento);
                }
            }
        }

        for (Item item : items) {
            if (descuentosGenerales.containsKey(item.description)) {
                item.discount += descuentosGenerales.get(item.description);
            }
        }

        return items;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        String base = System.getProperty("user.dir");
        String filePdf = base + "/src/test/resources/Compra.pdf";
        String fileTxt = base + "/src/test/resources/Compra.txt";
        String commandToParse = "pdftotext -layout " + filePdf + " " + fileTxt;
        String commandToDelete = "rm " + fileTxt;

        if (!Files.exists(Path.of(filePdf))) {
            System.err.println("File not created: " + filePdf);
            return;
        }

        CommandExecutor.execute(commandToParse);
        List<String> fileLines = Files.readAllLines(Path.of(fileTxt));

        List<Item> items = parseReceipt(fileLines);
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("Total: " + items.stream().mapToInt(i -> i.numberOfItems * i.amountPerUnit - i.discount).sum() / 100.0);
        System.out.println(items.size() + " items");
        CommandExecutor.execute(commandToDelete);

    }
}


class CommandExecutor {
    public static void execute(String commando) {
        try {
            Process proceso = Runtime.getRuntime().exec(commando);
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            BufferedReader errorLector = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
            String error;
            while ((error = errorLector.readLine()) != null) {
                System.err.println(error);
            }

            int codigoSalida = proceso.waitFor();
            System.out.println("Código de salida: " + codigoSalida);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}