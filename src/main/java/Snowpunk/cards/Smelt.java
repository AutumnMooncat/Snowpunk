package Snowpunk.cards;

import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.VentMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Smelt extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Smelt.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1, BLOCK = 5, UPG_BLOCK = 3, UP_COST = 0;

    public Smelt() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCK;
        misc = -1;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (Wiz.adp().hand.contains(this)) {
            misc = Wiz.adp().hand.group.indexOf(this);
        }
        if (misc != -1) {
            //int handIndex = Wiz.adp().hand.group.indexOf(this);
            if (misc > 0) {
                Wiz.atb(new ModCardTempAction(Wiz.adp().hand.group.get(misc-1), 1));
            }
            if (!Wiz.adp().hand.contains(this)) {
                misc--;
            }
            if (misc < Wiz.adp().hand.size()-1) {
                Wiz.atb(new ModCardTempAction(Wiz.adp().hand.group.get(misc+1), 1));
            }
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    Smelt.this.misc = -1;
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        return super.makeStatEquivalentCopy();
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(UPG_BLOCK));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new VentMod()), new int[]{}, new int[]{2});
        addUpgradeData(() -> upgradeBaseCost(UP_COST), new int[]{}, new int[]{1});
    }
}