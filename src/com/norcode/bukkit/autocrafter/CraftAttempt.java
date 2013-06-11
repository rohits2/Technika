package com.norcode.bukkit.autocrafter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.FreeSpace2.Technika.Technika;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftAttempt
{
  private final Technika plugin;
  private final Recipe recipe;
  private Inventory inventory;
  private ItemStack result = null;
  private Boolean canCraft = null;
  private Inventory cloneInv = null;
  private ItemStack fired = null;

  public CraftAttempt(Technika plugin, Recipe r, Inventory inventory, ItemStack fired) { this.recipe = r;
    this.fired = fired;
    this.inventory = inventory;
    this.plugin = plugin; }

  public ItemStack getResult()
  {
    return this.result;
  }

  public static List<ItemStack> getIngredients(Recipe recipe) {
    List<ItemStack> ingredients = new ArrayList<ItemStack>();
    if ((recipe instanceof ShapedRecipe)) {
      ShapedRecipe sr = (ShapedRecipe)recipe;
      String[] shape = sr.getShape();

      for (String row : shape) {
        for (int i = 0; i < row.length(); i++) {
          ItemStack stack = (ItemStack)sr.getIngredientMap().get(Character.valueOf(row.charAt(i)));
          for (ItemStack ing : ingredients) {
            int mss = ing.getType().getMaxStackSize();
            if ((ing.isSimilar(stack)) && (ing.getAmount() < mss)) {
              int canAdd = mss - ing.getAmount();
              int add = Math.min(canAdd, stack.getAmount());
              ing.setAmount(ing.getAmount() + add);
              int remaining = stack.getAmount() - add;
              if (remaining >= 1) {
                stack.setAmount(remaining);
              } else {
                stack = null;
                break;
              }
            }
          }
          if ((stack != null) && (stack.getAmount() > 0))
            ingredients.add(stack);
        }
      }
    }
    else if ((recipe instanceof ShapelessRecipe)) {
      for (ItemStack i : ((ShapelessRecipe)recipe).getIngredientList()) {
        for (ItemStack ing : ingredients) {
          int mss = ing.getType().getMaxStackSize();
          if ((ing.isSimilar(i)) && (ing.getAmount() < mss)) {
            int canAdd = mss - ing.getAmount();
            ing.setAmount(ing.getAmount() + Math.min(canAdd, i.getAmount()));
            int remaining = i.getAmount() - Math.min(canAdd, i.getAmount());
            if (remaining < 1) break;
            i.setAmount(remaining);
          }

        }

        if (i.getAmount() > 0) {
          ingredients.add(i);
        }
      }
    }
    return ingredients;
  }

  public static Inventory cloneInventory(JavaPlugin plugin, Inventory inv) {
    Inventory inv2 = plugin.getServer().createInventory(null, inv.getSize());
    for (int i = 0; i < inv.getSize(); i++) {
      inv2.setItem(i, inv.getItem(i) == null ? null : inv.getItem(i).clone());
    }
    return inv2;
  }

  public static boolean removeItem(Inventory inv, Material mat, int data, int qty)
  {
    int remd = 0;

    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < inv.getSize(); i++) {
      ItemStack s = inv.getItem(i);

      if (s != null)
      {
        if (remd < qty)
        {
          if ((s.getType().equals(mat)) && ((data == -1) || (data == s.getData().getData()))) {
            int take = Math.min(s.getAmount(), qty - remd);
            map.put(Integer.valueOf(i), Integer.valueOf(take));
            remd += take;

            if (take == s.getAmount()){
              inv.setItem(i, null);
            }else {
              s.setAmount(s.getAmount() - take);
            }
          }
        }
      }
    }
    if (remd != qty) {
      return false;
    }
    return true;
  }

  public boolean canCraft()
  {
    if (this.canCraft == null) {
      List<ItemStack> ingredients = getIngredients(this.recipe);
      this.cloneInv = cloneInventory(this.plugin, this.inventory);
      this.cloneInv.addItem(new ItemStack[] { this.fired.clone() });
      this.result = this.recipe.getResult().clone();
      this.canCraft = Boolean.valueOf(true);
      for (ItemStack stack : ingredients) {
        if (!removeItem(this.cloneInv, stack.getType(), stack.getData().getData(), stack.getAmount())) {
          this.canCraft = Boolean.valueOf(false);
          break;
        }
      }
    }

    return this.canCraft.booleanValue();
  }

  public void removeItems() {
    for (ItemStack stack : getIngredients(this.recipe))
      removeItem(this.inventory, stack.getType(), stack.getData().getData(), stack.getAmount());
  }
}