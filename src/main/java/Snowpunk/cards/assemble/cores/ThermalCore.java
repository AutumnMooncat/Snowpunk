package Snowpunk.cards.assemble.cores;

import Snowpunk.cards.assemble.AssembledCard;
import Snowpunk.cards.assemble.CoreCard;
import Snowpunk.powers.ChillPower;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.SingePower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Snowpunk.SnowpunkMod.makeID;

@NoPools
@NoCompendium
public class ThermalCore extends CoreCard {
    public static final String ID = makeID(ThermalCore.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, MAGIC = 6;

    public ThermalCore() {
        super(ID, COST, TYPE, EffectTag.CORE, EffectTag.MAGIC);
        secondMagic = baseSecondMagic = MAGIC;
        target = CardTarget.ENEMY;
    }

    @Override
    public ArrayList<Integer> unavailableTurns() {
        ArrayList<Integer> turns = new ArrayList<>();
        turns.add(0);
        turns.add(1);
        return turns;
    }

    @Override
    public void onUseEffect(AbstractPlayer player, AbstractMonster monster, AssembledCard card) {
        if (card.target == CardTarget.ALL_ENEMY) {
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    Wiz.applyToEnemy(mo, new SingePower(mo, player, card.secondMagic));
                    Wiz.applyToEnemy(mo, new ChillPower(mo, card.secondMagic));
                }
            }
        } else {
            Wiz.applyToEnemy(monster, new SingePower(monster, player, card.secondMagic));
            Wiz.applyToEnemy(monster, new ChillPower(monster, card.secondMagic));
        }
    }

    @Override
    public int getUpgradeAmount() {
        return 3;
    }
}
