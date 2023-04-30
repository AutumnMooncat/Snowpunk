package Snowpunk.cards;

import Snowpunk.actions.ChangeChristmasSpiritAction;
import Snowpunk.actions.PlayLinkedCardsInHandAction;
import Snowpunk.cardmods.EverburnMod;
import Snowpunk.cards.abstracts.AbstractEasyCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.powers.FireballPower;
import Snowpunk.powers.NextTurnPowerPower;
import Snowpunk.powers.SnowballPower;
import Snowpunk.util.KeywordManager;
import Snowpunk.util.Wiz;
import basemod.BaseMod;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Snowpunk.SnowpunkMod.makeID;

@NoCompendium
@NoPools
public class HotChocolate extends AbstractEasyCard {
    public final static String ID = makeID(HotChocolate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    private static ArrayList<TooltipInfo> Tooltip;

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (Tooltip == null) {
            Tooltip = new ArrayList<>();
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.SNOW), BaseMod.getKeywordDescription(KeywordManager.SNOW)));
            Tooltip.add(new TooltipInfo(BaseMod.getKeywordProper(KeywordManager.FIRE), BaseMod.getKeywordDescription(KeywordManager.FIRE)));
        }
        return Tooltip;
    }

    public HotChocolate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = 1;
        CardTemperatureFields.addInherentHeat(this, 1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FireballPower(p, magicNumber));
        Wiz.applyToSelf(new SnowballPower(p, magicNumber));
    }

    public void upp() {
        CardModifierManager.addModifier(this, new EverburnMod());
    }
}