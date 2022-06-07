package Snowpunk.cardmods.cores;

import Snowpunk.cards.cores.AssembledCard;
import Snowpunk.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ApplyAOEVulnMod extends AbstractCoreCardMod {
    int effect;
    int upEffect;

    public ApplyAOEVulnMod(String name, String description, AbstractCard.CardType type, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target, int effect, int upEffect) {
        super(name, description, type, rarity, target);
        this.effect = effect;
        this.upEffect = upEffect;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        collateCardBasics(card);
        collateMagic(card, effect, upEffect);
        if (card instanceof AssembledCard) {
            ((AssembledCard) card).addUseConsumer((p, m) -> {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (!mo.isDeadOrEscaped()) {
                        if (useSecondVar) {
                            Wiz.atb(new ApplyPowerAction(mo, p, new VulnerablePower(mo, ((AssembledCard) card).secondMagic, false)));
                        } else {
                            Wiz.atb(new ApplyPowerAction(mo, p, new VulnerablePower(mo, card.magicNumber, false)));
                        }
                    }
                }
            });
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyAOEVulnMod(name, description, type, rarity, target, effect, upEffect);
    }
}
