package com.example.proyecto_final_iot.Supervisor.Entity;

public class EquipoData {
    private String equipmentName;
    private String typeEq;

    public EquipoData(String equipmentName, String typeEq) {
        this.equipmentName = equipmentName;
        this.typeEq = typeEq;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getTypeEq() {
        return typeEq;
    }
}
