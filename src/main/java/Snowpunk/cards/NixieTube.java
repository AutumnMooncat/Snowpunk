package Snowpunk.cards;

import Snowpunk.actions.EnhanceCardInHardAction;
import Snowpunk.actions.MultiUpgradeInHandAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class NixieTube extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(NixieTube.class.getSimpleName());

    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    private boolean addGear = false;

    public NixieTube() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        info = baseInfo = 0;
        CardModifierManager.addModifier(this, new GearMod(2));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int gears = getGears();
        List<AbstractCardModifier> mods = new ArrayList<>();
        mods.add(new PlateMod(gears));
        if (addGear)
            mods.add(new GearMod(1));
        Wiz.atb(new EnhanceCardInHardAction(1, info > 0 ? gears : 1, mods));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(2)));
        addUpgradeData(() -> {
            addGear = true;
            uDesc();
            CardModifierManager.addModifier(this, new GearMod(0));
        });
        addUpgradeData(() -> upgradeInfo(1));
    }
}