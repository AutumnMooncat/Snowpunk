package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.BrassPower;
import Snowpunk.powers.ChooChooPower;
import Snowpunk.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.getSFXFrequency;
import static Snowpunk.SnowpunkMod.makeID;

public class ChooChoo extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChooChoo.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static int playSound = 99;

    private static final int COST = 5, BLOCk = 30;

    public ChooChoo() {
        super(ID, COST, TYPE, RARITY, TARGET);
        block = baseBlock = BLOCk;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        playSound++;
        if (playSound >= getSFXFrequency() && getSFXFrequency() > 0) {
            addToBot(new SFXAction("snowpunk:choochoo"));
            playSound = 0;
        }
        blck();
        Wiz.applyToSelf(new ChooChooPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeBlock(10));
        addUpgradeData(() -> upgradeBaseCost(4));
    }
}