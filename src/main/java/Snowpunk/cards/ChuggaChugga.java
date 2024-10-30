package Snowpunk.cards;

import Snowpunk.actions.DelayedMakeCopyAction;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class ChuggaChugga extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(ChuggaChugga.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public ChuggaChugga() {
        super(ID, COST, TYPE, RARITY, TARGET);
        cardsToPreview = new ChooChoo();
        info = baseInfo = 0;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            cardsToPreview.upgrade();
            upgradeInfo(1);
        });
        addUpgradeData(() -> {
            cardsToPreview.upgrade();
            upgradeInfo(1);
        });
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod(1)));
        setDependencies(true, 1, 0);
    }
}