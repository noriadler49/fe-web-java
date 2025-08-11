<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, java.text.*" %>
    <%@ page import="com.mycompany.maven.mvc.project.model.Order" %>
        <% String contextPath=request.getContextPath(); com.mycompany.maven.mvc.project.model.Order
            order=(com.mycompany.maven.mvc.project.model.Order) request.getAttribute("order");
            List<com.mycompany.maven.mvc.project.model.OrderItem> items = (List
            <com.mycompany.maven.mvc.project.model.OrderItem>) request.getAttribute("items");
                Date createdAtDate = (Date) request.getAttribute("createdAtDate");

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                %>

                <!DOCTYPE html>
                <html>

                <head>
                    <title>Order Confirmation</title>
                    <link rel="stylesheet"
                        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
                    <style>
                        .order-success {
                            background-color: #d4edda;
                            border: 1px solid #c3e6cb;
                            border-radius: 0.375rem;
                            padding: 1rem;
                            margin-bottom: 1rem;
                        }

                        .order-info {
                            background-color: #f8f9fa;
                            border-radius: 0.375rem;
                            padding: 1rem;
                            margin-bottom: 1rem;
                        }
                    </style>
                </head>

                <body>
                    <div class="container mt-5">
                        <div class="row justify-content-center">
                            <div class="col-md-8">
                                <div class="order-success">
                                    <h2 class="text-success mb-3">
                                        <i class="fas fa-check-circle"></i> Order Placed Successfully!
                                    </h2>
                                    <p class="mb-0">Thank you for your order. Your order has been confirmed and is being
                                        processed.</p>
                                </div>

                                <div class="order-info">
                                    <h4>Order Information</h4>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <p><strong>Order ID:</strong>
                                                <%= order.getOrderId() %>
                                            </p>
                                            <p><strong>Status:</strong>
                                                <span class="badge bg-warning text-dark">
                                                    <%= order.getStatus() %>
                                                </span>
                                            </p>
                                        </div>
                                        <div class="col-md-6">

                                            <p><strong>Total:</strong>
                                                <strong class="text-primary">
                                                    <%= currencyFormat.format(order.getTotalPrice()) %>
                                                </strong>
                                            </p>
                                        </div>
                                    </div>

                                    <% if (order.getVoucherCode() !=null && !order.getVoucherCode().isEmpty()) { %>
                                        <p><strong>Voucher Applied:</strong>
                                            <%= order.getVoucherCode() %>
                                        </p>
                                        <% } %>
                                </div>

                                <div class="card">
                                    <div class="card-header">
                                        <h4 class="mb-0">Order Details</h4>
                                    </div>
                                    <div class="card-body">
                                        <% if (items !=null && !items.isEmpty()) { %>
                                            <div class="table-responsive">
                                                <table class="table table-hover">
                                                    <thead class="table-light">
                                                        <tr>
                                                            <th>Dish</th>
                                                            <th class="text-center">Quantity</th>
                                                            <th class="text-end">Unit Price</th>
                                                            <th class="text-end">Subtotal</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <% for (com.mycompany.maven.mvc.project.model.OrderItem item :
                                                            items) { %>
                                                            <tr>
                                                                <td>
                                                                    <strong>
                                                                        <%= item.getDishName() %>
                                                                    </strong><br>
                                                                    <small class="text-muted">ID: <%= item.getDishId()
                                                                            %></small><br>
                                                                    <small class="text-muted">Price / Item: <%=
                                                                            currencyFormat.format(item.getPrice()) %>
                                                                    </small>
                                                                </td>

                                                                <td class="text-center">
                                                                    <span class="badge bg-secondary">
                                                                        <%= item.getQuantity() %>
                                                                    </span>
                                                                </td>
                                                                <td class="text-end">
                                                                    <%= currencyFormat.format(item.getPrice()) %>
                                                                </td>
                                                                <td class="text-end">
                                                                    <strong>
                                                                        <%= currencyFormat.format(item.getPrice() *
                                                                            item.getQuantity()) %>
                                                                    </strong>
                                                                </td>

                                                            </tr>
                                                            <% } %>
                                                    </tbody>
                                                    <tfoot class="table-light">
                                                        <tr>
                                                            <th colspan="3" class="text-end">Total Amount:</th>
                                                            <th class="text-end text-primary">
                                                                <%= currencyFormat.format(order.getTotalPrice()) %>
                                                            </th>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                            <% } else { %>
                                                <div class="alert alert-warning">
                                                    <i class="fas fa-exclamation-triangle"></i>
                                                    No items found for this order.
                                                </div>
                                                <% } %>
                                    </div>
                                </div>

                                <div class="mt-4 text-center">
                                    <a href="<%= contextPath %>/menu?category=All" class="btn btn-primary btn-lg">
                                        <i class="fas fa-shopping-cart"></i> Continue Shopping
                                    </a>
                                    <a href="<%= contextPath %>/myOrders" class="btn btn-outline-secondary btn-lg ms-2">
                                        <i class="fas fa-list"></i> View All Orders
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/js/all.min.js"></script>
                </body>

                </html>