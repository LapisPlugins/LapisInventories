/*
 * Copyright 2020 Benjamin Martin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lapsimc.lapisinventories.api;

import net.lapsimc.lapisinventories.LapisInventories;
import org.bukkit.entity.Player;

public class LoginAPI {

    public static boolean isLoginEnabled;

    //TODO: This might not be needed
    public void enableLogin() {
        isLoginEnabled = true;
    }

    public void hideInventory(Player p) {
        ((LapisInventories) LapisInventories.getInstance()).getPlayer(p.getUniqueId()).hideInventory();
    }

    public void restoreInventory(Player p) {
        ((LapisInventories) LapisInventories.getInstance()).getPlayer(p.getUniqueId()).restoreInventory();
    }

}