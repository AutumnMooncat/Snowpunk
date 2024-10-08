package Snowpunk.cards;

import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cardmods.PlateMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

public class FiveGoldenRings extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(FiveGoldenRings.class.getSimpleName());

    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(FiveGoldenRings.ID);

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static int playSound = 0;

    private static final int COST = 1;

    public FiveGoldenRings() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //magicNumber = baseMagicNumber = 5;
        exhaust = true;
    }

    @Override
    public float getTitleFontSize() {
        return 19f;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (playSound == 0)
            addToBot(new SFXAction("snowpunk:FIVEGOLDENRINGS"));

        Wiz.atb(new TalkAction(true, cardStrings.EXTENDED_DESCRIPTION[0], 2, 2));
        if (playSound == 0) {
            addToBot(new WaitAction(.1f));
            addToBot(new WaitAction(.1f));
            addToBot(new WaitAction(.1f));
        }

        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : Wiz.adp().hand.group) {
                    CardModifierManager.addModifier(card, new PlateMod(5, true));
                    card.superFlash(Color.WHITE.cpy());
                }
                isDone = true;
            }
        });
        if (playSound == 0) {
            addToBot(new WaitAction(.1f));
            addToBot(new WaitAction(.1f));
            addToBot(new WaitAction(.1f));
        }
        playSound++;
        if (playSound > 10)
            playSound = 0;
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> {
            exhaust = false;
            uDesc();
        });
        addUpgradeData(() -> CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}