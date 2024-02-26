package Snowpunk.cards;

import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.SteamFormPower;
import Snowpunk.util.Wiz;
import basemod.AutoAdd;
import basemod.helpers.BaseModCardTags;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Snowpunk.SnowpunkMod.makeID;

public class SteamForm extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(SteamForm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 4;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    private boolean energy;
    public SteamForm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        tags.add(BaseModCardTags.FORM);
        energy = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energy)
            Wiz.atb(new GainEnergyAction(1));
        if (magicNumber > 0)
            Wiz.atb(new DrawCardAction(1));

        Wiz.applyToSelf(new SteamFormPower(p, 1));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            energy = true;
            uDesc();
        });
        addUpgradeData(() -> {
            magicNumber = baseMagicNumber = 0;
            upgradeMagicNumber(1);
        });
    }
}