package me.icanttellyou.tweakedcreativemenu.util;

import me.icanttellyou.tweakedcreativemenu.data.DataItemGroup;
import me.icanttellyou.tweakedcreativemenu.data.DataItemGroupManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemGroupUtil {
    public static Identifier getGroupIdentifier(ItemGroup group) {
        if (group instanceof DummyItemGroup dummyGroup)
            return dummyGroup.getIdentifier();

        return Registries.ITEM_GROUP.getId(group);
    }

    public static List<ItemGroup> addCustomItemGroups(List<ItemGroup> original) {
        List<ItemGroup> groups = new ArrayList<>(original);
        for (var data : DataItemGroupManager.groupData.entrySet()) {
            DataItemGroup dataGroup = data.getValue();
            boolean vanilla = Registries.ITEM_GROUP.get(data.getKey()) != null;
            ItemGroup group;

            if (vanilla) {
                group = Registries.ITEM_GROUP.get(data.getKey());
            } else {
                group = (ItemGroup) dataGroup.getDummyItemGroup();
            }

            if (dataGroup.insertion != null) {
                ItemGroup target = groups.stream().filter(g ->
                        getGroupIdentifier(g).equals(dataGroup.insertion.target())).findFirst().orElse(null);

                if (target != null && group.hasStacks()) {
                    int index = groups.indexOf(target);
                    DataItemGroup.Insertion.Order order = dataGroup.insertion.order();

                    if (vanilla) {
                        if (groups.indexOf(group) < index) {
                            index--;
                        }

                        groups.remove(group);
                    }

                    if (order == DataItemGroup.Insertion.Order.AFTER) {
                        index += 1;
                    }

                    groups.add(index, group);
                    continue;
                }
            }


            if (!groups.contains(group) && group.hasStacks()) {
                groups.add(group);
            }
        }

        return groups;
    }
}
