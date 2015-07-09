package lib.usagerights

import com.gu.mediaservice.model._
import org.scalatest.{FunSpec, Matchers}

class CostCalculatorTest extends FunSpec with Matchers {

  describe("from usage rights") {

    it("should be free with a free category") {
      val usageRights = ImageUsageRights(category = Some(Handout))
      val cost = CostCalculator.getCost(usageRights)

      cost should be (Some(Free))
    }

    it("should be conditional with a free category and restrictions") {
      val usageRights = ImageUsageRights(
        category = Some(Handout),
        restrictions = Some("Restrictions")
      )
      val cost = CostCalculator.getCost(usageRights)

      cost should be (Some(Conditional))
    }

    it("should be free with a free supplier") {
      val usageRights = ImageUsageRights(
        category = Some(Agency),
        supplier = Some("Getty Images")
      )
      val cost = CostCalculator.getCost(usageRights)

      cost should be (Some(Free))
    }

    it("should not be free with a free supplier but excluded collection") {
      val usageRights = ImageUsageRights(
        category = Some(Agency),
        supplier = Some("Getty Images"),
        suppliersCollection = Some("Anadolu")
      )
      val cost = CostCalculator.getCost(usageRights)

      cost should be (None)
    }
  }
}