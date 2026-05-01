package controllers;

import DTOs.ProductData;
import models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static play.libs.Scala.asScala;

/**
 * An example of form processing.
 *
 * https://playframework.com/documentation/latest/JavaForms
 */
@Singleton
public class ProductController extends Controller {

    private final Form<ProductData> form;
    private MessagesApi messagesApi;
    private final List<Product> products;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public ProductController(FormFactory formFactory, MessagesApi messagesApi) {
        this.form = formFactory.form(ProductData.class);
        this.messagesApi = messagesApi;
        this.products = com.google.common.collect.Lists.newArrayList(
                new Product("Notebook Dell", 700),
                new Product("Notebook Acer", 600)
        );
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result listProducts(Http.Request request) {
        return ok(views.html.listProducts.render(asScala(products), form, request, messagesApi.preferred(request)));
    }

    public Result createProduct(Http.Request request) {
        final Form<ProductData> boundForm = form.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            return badRequest(views.html.listProducts.render(asScala(products), boundForm, request, messagesApi.preferred(request)));
        } else {
            ProductData data = boundForm.get();
            products.add(new Product(data.getName(), data.getPrice()));
            return redirect(routes.ProductController.listProducts())
                .flashing("info", "Product added!");
        }
    }
}
