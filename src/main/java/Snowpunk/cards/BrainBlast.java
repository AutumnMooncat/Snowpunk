package Snowpunk.cards;

import Snowpunk.actions.ApplyCardModifierAction;
import Snowpunk.actions.BetterSelectCardsCenteredAction;
import Snowpunk.actions.IncreaseModifiersAction;
import Snowpunk.actions.ReturnAction;
import Snowpunk.cardmods.GearMod;
import Snowpunk.cardmods.HatMod;
import Snowpunk.cards.abstracts.AbstractMultiUpgradeCard;
import Snowpunk.patches.CardTemperatureFields;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Snowpunk.SnowpunkMod.makeID;

public class BrainBlast extends AbstractMultiUpgradeCard {
    public final static String ID = makeID(BrainBlast.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public BrainBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        damage = baseDamage = 9;
//        CardTemperatureFields.addInherentHeat(this, CardTemperatureFields.COLD);
        CardModifierManager.addModifier(this, new GearMod(1));
    }

    public void use(AbstractPlayer player, AbstractMonster m) {
        Wiz.atb(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1F));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (getGears() > 0)
            Wiz.atb(new ReturnAction(getGears()));
        //Wiz.atb(new IncreaseModifiersAction(Wiz.adp().hand, magicNumber));
    }

    @Override
    public void addUpgrades() {
        addUpgradeData(() -> upgradeDamage(3));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new GearMod(1)));
        addUpgradeData(() -> CardModifierManager.addModifier(this, new HatMod()));
    }
}