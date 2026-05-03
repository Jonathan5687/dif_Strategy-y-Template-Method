package com.mycompany.strategy_y_templete_method;

import javax.swing.*;
import java.awt.*;

// Clase principal
public class PersonajeApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}

// ===== STRATEGY =====
interface Movimiento {
    String mover(String nombre);
}

class Caminar implements Movimiento {
    public String mover(String nombre) {
        return nombre + " está caminando";
    }
}

class Correr implements Movimiento {
    public String mover(String nombre) {
        return nombre + " está corriendo";
    }
}

class Saltar implements Movimiento {
    public String mover(String nombre) {
        return nombre + " está saltando";
    }
}

// ===== TEMPLATE METHOD =====
abstract class AccionPersonaje {

    public final String realizarAccion(String nombre) {
        StringBuilder sb = new StringBuilder();
        sb.append(iniciar(nombre)).append("\n");
        sb.append(ejecutarAccion(nombre)).append("\n");
        sb.append(finalizar(nombre));
        return sb.toString();
    }

    protected String iniciar(String nombre) {
        return nombre + " se prepara...";
    }

    protected abstract String ejecutarAccion(String nombre);

    protected String finalizar(String nombre) {
        return nombre + " termina la acción.";
    }
}

class AtacarAccion extends AccionPersonaje {
    protected String ejecutarAccion(String nombre) {
        return nombre + " lanza un ataque poderoso️";
    }
}

class DefenderAccion extends AccionPersonaje {
    protected String ejecutarAccion(String nombre) {
        return nombre + " levanta un escudo️";
    }
}

// ===== MODELO =====
class Personaje {
    private String nombre;
    private Movimiento movimiento;

    public Personaje(String nombre) {
        this.nombre = nombre;
        this.movimiento = new Caminar();
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public String ejecutarMovimiento() {
        return movimiento.mover(nombre);
    }

    public String ejecutarAccion(AccionPersonaje accion) {
        return accion.realizarAccion(nombre);
    }
}

// ===== VISTA =====
class VentanaPrincipal extends JFrame {

    private JTextArea areaStrategy;
    private JTextArea areaTemplate;
    private Personaje personaje;

    public VentanaPrincipal() {
        personaje = new Personaje("Héroe");

        setTitle("Strategy vs Template Method");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponentes();
        setVisible(true);
    }

    private void initComponentes() {
        setLayout(new GridLayout(1, 2));

        // ===== PANEL STRATEGY =====
        JPanel panelStrategy = new JPanel(new BorderLayout());
        panelStrategy.setBorder(BorderFactory.createTitledBorder("STRATEGY (Cambio de comportamiento dinámico)"));

        areaStrategy = new JTextArea();
        areaStrategy.setEditable(false);
        panelStrategy.add(new JScrollPane(areaStrategy), BorderLayout.CENTER);

        JPanel botonesStrategy = new JPanel(new GridLayout(2, 2, 5, 5));

        JButton btnCaminar = new JButton("Caminar");
        JButton btnCorrer = new JButton("Correr");
        JButton btnSaltar = new JButton("Saltar");
        JButton btnMover = new JButton("Ejecutar");

        botonesStrategy.add(btnCaminar);
        botonesStrategy.add(btnCorrer);
        botonesStrategy.add(btnSaltar);
        botonesStrategy.add(btnMover);

        panelStrategy.add(botonesStrategy, BorderLayout.SOUTH);

        // ===== PANEL TEMPLATE =====
        JPanel panelTemplate = new JPanel(new BorderLayout());
        panelTemplate.setBorder(BorderFactory.createTitledBorder("TEMPLATE METHOD (Algoritmo fijo, pasos variables)"));

        areaTemplate = new JTextArea();
        areaTemplate.setEditable(false);
        panelTemplate.add(new JScrollPane(areaTemplate), BorderLayout.CENTER);

        JPanel botonesTemplate = new JPanel(new GridLayout(1, 2, 5, 5));

        JButton btnAtacar = new JButton("Atacar");
        JButton btnDefender = new JButton("Defender");

        botonesTemplate.add(btnAtacar);
        botonesTemplate.add(btnDefender);

        panelTemplate.add(botonesTemplate, BorderLayout.SOUTH);

        add(panelStrategy);
        add(panelTemplate);

        // ===== EVENTOS STRATEGY =====
        btnCaminar.addActionListener(e -> {
            personaje.setMovimiento(new Caminar());
            mostrarStrategy("Estrategia cambiada a CAMINAR");
        });

        btnCorrer.addActionListener(e -> {
            personaje.setMovimiento(new Correr());
            mostrarStrategy("Estrategia cambiada a CORRER");
        });

        btnSaltar.addActionListener(e -> {
            personaje.setMovimiento(new Saltar());
            mostrarStrategy("Estrategia cambiada a SALTAR");
        });

        btnMover.addActionListener(e -> mostrarStrategy(personaje.ejecutarMovimiento()));

        // ===== EVENTOS TEMPLATE =====
        btnAtacar.addActionListener(e -> {
            AccionPersonaje accion = new AtacarAccion();
            mostrarTemplate(personaje.ejecutarAccion(accion));
        });

        btnDefender.addActionListener(e -> {
            AccionPersonaje accion = new DefenderAccion();
            mostrarTemplate(personaje.ejecutarAccion(accion));
        });
    }

    private void mostrarStrategy(String texto) {
        areaStrategy.append(texto + "\n");
    }

    private void mostrarTemplate(String texto) {
        areaTemplate.append(texto + "\n\n");
    }
}
