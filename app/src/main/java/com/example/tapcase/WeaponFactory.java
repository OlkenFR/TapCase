package com.example.tapcase;


import androidx.appcompat.app.AppCompatActivity;

public class WeaponFactory extends AppCompatActivity {
    public Arme createWeapon(Skins weaponID) {
        switch (weaponID) {
            case GLOCK_ORIGINAL:
                return new Arme(getResources().getDrawable(R.drawable.glock_classique), Rarete.BASE, 1, 1, "Glock-17");
            case GLOCK_WEASEL:
                return new Arme(getResources().getDrawable(R.drawable.glock_weasel), Rarete.SUPERIEUR, 2, 10, "Glock-17 Weasel");
            case GLOCK_WATER_ELEMENT:
                return new Arme(getResources().getDrawable(R.drawable.glock_water_element), Rarete.EXOTIQUE, 4, 25, "Glock-17 Water element");
            case GLOCK_BLACK_NEO:
                return new Arme(getResources().getDrawable(R.drawable.glock_neo_noir), Rarete.EXOTIQUE, 7, 40, "Glock-17 Black-Neo");
            case GLOCK_BULLET_QUEEN:
                return new Arme(getResources().getDrawable(R.drawable.glock_bullet_queen), Rarete.CLASSIFIE, 15, 75, "Glock-17 Bullet queen");
            case USP_CYREX:
                return new Arme(getResources().getDrawable(R.drawable.usp_cyrex), Rarete.BASE, 22, 78, "USP-S Cyrex");
            case USP_FLASHBACK:
                return new Arme(getResources().getDrawable(R.drawable.usp_flashback), Rarete.SUPERIEUR, 7, 28, "USP-S Flashback");
            case USP_ORION:
                return new Arme(getResources().getDrawable(R.drawable.usp_orion), Rarete.EXOTIQUE, 12, 37, "USP-S Orion");
            case USP_TRAITOR:
                return new Arme(getResources().getDrawable(R.drawable.usp_traitor), Rarete.CLASSIFIE, 18, 67, "USP-S The traitor");
            case USP_KILL_CONFIRMED:
                return new Arme(getResources().getDrawable(R.drawable.usp_kill_confirmed), Rarete.CLASSIFIE, 28, 98, "USP-S Kill confirmed");
            case MP9_DRY_SEASON:
                return new Arme(getResources().getDrawable(R.drawable.mp_dry_season), Rarete.BASE, 12, 40, "MP9 Dry season");
            case MP9_HYDRA:
                return new Arme(getResources().getDrawable(R.drawable.mp_hydra), Rarete.SUPERIEUR, 16, 54, "MP9 Hydra");
            case MP9_GOO:
                return new Arme(getResources().getDrawable(R.drawable.mp_goo), Rarete.SUPERIEUR, 20, 67, "MP9 Goo");
            case MP9_MOUNT_FUDJI:
                return new Arme(getResources().getDrawable(R.drawable.mp_mount_fuji), Rarete.EXOTIQUE, 25, 95, "MP9 Mount Fudji");
            case MP9_FOOD_CHAIN:
                return new Arme(getResources().getDrawable(R.drawable.mp_food_chain), Rarete.CLASSIFIE, 30, 120, "MP9 Food chain");
            case M4_NITRO:
                return new Arme(getResources().getDrawable(R.drawable.m_nitro), Rarete.BASE, 17, 60, "M4A1-S Nitro");
            case M4_CYREX:
                return new Arme(getResources().getDrawable(R.drawable.m_cyrex), Rarete.SUPERIEUR, 22, 78, "M4A1-S Cyrex");
            case M4_PRINTSTREAM:
                return new Arme(getResources().getDrawable(R.drawable.m_printstream), Rarete.EXOTIQUE, 28, 110, "M4A1-S Printstream");
            case M4_HYPER_BEAST:
                return new Arme(getResources().getDrawable(R.drawable.m_hyperbeast), Rarete.CLASSIFIE, 32, 144, "M4A1-S Hyperbeast");
            case M4_IMMINENT_DANGER:
                return new Arme(getResources().getDrawable(R.drawable.m_imminent_danger), Rarete.CLASSIFIE, 39, 176, "M4A1-S Danger");
            case AK47_ELITE_BUILD:
                return new Arme(getResources().getDrawable(R.drawable.ak_elite_build), Rarete.BASE, 28, 108, "AK-47 Elite build");
            case AK47_POINT_DISARRAY:
                return new Arme(getResources().getDrawable(R.drawable.ak_point_disarray), Rarete.SUPERIEUR, 33, 128, "AK-47 Dissaray point");
            case AK47_PANTHERA:
                return new Arme(getResources().getDrawable(R.drawable.ak_panthera), Rarete.EXOTIQUE, 37, 156, "AK-47 Panthera");
            case AK47_BLOODSPORT:
                return new Arme(getResources().getDrawable(R.drawable.ak_bloodsport), Rarete.CLASSIFIE, 42, 185, "AK-47 Bloodsport");
            case AK47_VULCAN:
                return new Arme(getResources().getDrawable(R.drawable.ak_vulcan), Rarete.CLASSIFIE, 51, 230, "AK-47 Vulcan");
            case AWP_WORM_GOD:
                return new Arme(getResources().getDrawable(R.drawable.awp_worm_god), Rarete.BASE, 44, 154, "AWP Worm god");
            case AWP_ATHERIS:
                return new Arme(getResources().getDrawable(R.drawable.awp_atheris), Rarete.SUPERIEUR, 53, 178, "AWP Atheris");
            case AWP_BLACK_NEO:
                return new Arme(getResources().getDrawable(R.drawable.awp_neo_noir), Rarete.EXOTIQUE, 60, 200, "AWP Black-Neo");
            case AWP_HYPER_BEAST:
                return new Arme(getResources().getDrawable(R.drawable.awp_hyperbeast), Rarete.CLASSIFIE, 69, 232, "AWP Hyperbeast");
            case AWP_DRAGON_LORE:
                return new Arme(getResources().getDrawable(R.drawable.awp_dragon_lore), Rarete.SECRET, 80, 300, "AWP Dragon lore");
            case KNIFE_CLASSIC:
                return new Arme(getResources().getDrawable(R.drawable.knife_boreal_forest), Rarete.SECRET, 80, 234, "Classic Boreal forest");
            case KNIFE_HUNTSMAN:
                return new Arme(getResources().getDrawable(R.drawable.knife_freehand), Rarete.SECRET, 91, 275, "Huntsman Freehand");
            case KNIFE_BAYONET:
                return new Arme(getResources().getDrawable(R.drawable.knife_fade), Rarete.SECRET, 108, 322, "Bayonet Fade");
            case KNIFE_KARAMBIT:
                return new Arme(getResources().getDrawable(R.drawable.knife_lore), Rarete.SECRET, 122, 366, "Karambit Lore");
            case KNIFE_BUTTERFLY:
                return new Arme(getResources().getDrawable(R.drawable.knife_doppler), Rarete.SECRET, 150, 540, "Butterfly Doppler");
            default:
                throw new IllegalArgumentException("Unknown weapon ID : " + weaponID);
        }
    }
}
