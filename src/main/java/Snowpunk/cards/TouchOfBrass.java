package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Snowpunk.SnowpunkMod.makeID;

public class TouchOfBrass extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(TouchOfBrass.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2, BRASS = 10;


    public TouchOfBrass() {
        super(ID, COST, TYPE, RARITY, TARGET);
        CardModifierManager.addModifier(this, new GearMod(BRASS));
        exhaust = true;

        if (CardCrawlGame.playerName.equals("rorDev")) {
            originalName = name = cardStrings.EXTENDED_DESCRIPTION[0];
            initializeTitle();
        }
    }

    @Override
    public boolean canUpgrade() {
        if (CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)//literally all of this is to check if in combat
            return super.canUpgrade();
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tempHP = getGears();
        Wiz.atb(new AddTemporaryHPAction(p, p, tempHP));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(5)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(5)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(5)));
        setDependencies(true, 1, 0);
        setDependencies(true, 2, 1);
    }
}