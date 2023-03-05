package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class Resupply extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Resupply.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int DRAW = 2;
    private static final int UP_DRAW = 1;

    public Resupply() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(DRAW));
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int drawAmount = getGears();
        if (drawAmount > 0) {
            Wiz.atb(new DrawCardAction(drawAmount, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (c.canUpgrade()) {
                            c.upgrade();
                        }
                    }
                    this.isDone = true;
                }
            }));
        }
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(UP_DRAW)));
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
    }
}