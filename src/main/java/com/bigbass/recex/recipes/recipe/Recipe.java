package com.bigbass.recex.recipes.recipe;

import com.bigbass.recex.recipes.VoltageLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Recipe {

  int id;
  Map<String, Integer> inputs = new HashMap<>();
  Map<String, Integer> outputs = new HashMap<>();

  VoltageLevel voltageLevel;
  Integer duration;
  Map<String, Integer> outputChances; // Keep NULL until used to not have to serialize

  public void addInput(String id, int amount) {
    inputs.put(id, inputs.getOrDefault(id, 0) + amount);
    updateId();
  }

  public void addOutput(String id, int amount) {
    outputs.put(id, outputs.getOrDefault(id, 0) + amount);
    updateId();
  }

  public void addOutputChance(String id, int chance) {
    if (outputChances == null) outputChances = new HashMap<>();
    outputChances.put(id, chance);
  }

  public void setVoltageLevel(VoltageLevel voltageLevel) {
    this.voltageLevel = voltageLevel;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Map<String, Integer> getInputs() {
    return inputs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Recipe that = (Recipe) o;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(inputs, outputs, voltageLevel, duration);
  }

  public void updateId() {
    this.id = hashCode();
  }

  public Recipe() {
  }

  public Recipe(Map<String, Integer> inputs, Map<String, Integer> outputs, VoltageLevel voltageLevel, Integer duration) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.voltageLevel = voltageLevel;
		this.duration = duration;
    updateId();
	}
}
