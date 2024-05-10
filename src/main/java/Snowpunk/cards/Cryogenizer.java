package Snowpunk.cards;

import Snowpunk.actions.CryogenizerAction;
import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.ModCardTempAction;
import Snowpunk.cardmods.ChillMod;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class Cryogenizer extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(Cryogenizer.class.getSimpleName());
    public static CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static String[] TEXT = strings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    public Cryogenizer() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 4;
        CardTemperatureFields.addHeat(this, CardTemperatureFields.COLD);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        List<AbstractCardModifier> mods = new ArrayList<>();
        mods.add(new PlateMod(magicNumber));
        mods.add(new ChillMod(magicNumber));
        Wiz.atb(new EnhanceCardInHardAction(1, 0, mods));
        //Wiz.atb(new CryogenizerAction(magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeMagicNumber(2));
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() ->
        {
            uDesc();
            exhaust = false;
        });
    }
}