package com.itechvision.ecrobo.pickman.ServerRetro;


import com.itechvision.ecrobo.pickman.Models.ArrivalWeight.ArrivalWeightRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodePickRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.BarcodePickResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus.GetBatchStatusResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.GetBatchStatus.GetBatchStatus_Req;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.SubmitBarcodeRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BarcodePickBatch.SubmitBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchPick_Request;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BatchpickResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxlistRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.BoxDetail.BoxlistResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.CheckTasorderRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.TasworkingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.CheckTasOrder.TasworkingRespose;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ClearBtotal.BtotalClearResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.ProductList.ProductlistResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasBarcoderesult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasPickBarcodeRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasSkipResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.TasSubmitResult;
import com.itechvision.ecrobo.pickman.Models.BatchPick.TasPicking.tasSubmitRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel.BatchListRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BatchListModel.BatchListResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchPicking.BoxBatchRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchPicking.BoxBatchResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchSubmisionModel.BoxBatchSubmitReq;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.BoxBatchSubmisionModel.BoxBatchSubmitResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.FixBatchPicking.FixBatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.FixBatchPicking.FixBatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.BatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.BatchPickingW6.GetBatchPicking.BatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxCashOrderIDResp;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList.BoxListRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxList.BoxListResp;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList.BoxProductClickRequest;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.BoxProductclickList.BoxProductclickListResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq.PickProductReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.FixReq.PickProductRes;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.GetBoxNoResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.NextBoxReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Packing.GetPackingListResponse;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Recreate.BoxRecreateReq;
import com.itechvision.ecrobo.pickman.Models.BoxCashRegisterModals.Recreate.BoxRecreateResponse;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.CustomerBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckBarcode.CustomerBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer.CustomerCheckReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.CheckCustomer.CustomerCheckResponse;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival.SubmitArrivalReq;
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival.SubmitArrivalResponse;
import com.itechvision.ecrobo.pickman.Models.DaimaruShipping.DaimaruKoguchiShipUpdatereq;
import com.itechvision.ecrobo.pickman.Models.DaimaruShipping.DaimaruShippingRequest;
import com.itechvision.ecrobo.pickman.Models.DaimaruShipping.Daimaru_GetOrderRespose;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.DirArrivalLoctionResult;
import com.itechvision.ecrobo.pickman.Models.DirectArrival.LocationReq;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBatchListModel.DmBatchListRequest;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBatchListModel.DmBatchListResponse;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchPicking.DmBoxBatchRequest;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchPicking.DmBoxBatchResponse;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchSubmisionModel.DmBoxBatchSubmitReq;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmBoxBatchSubmisionModel.DmBoxBatchSubmitResponse;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmFixBatchPicking.DmFixBatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmFixBatchPicking.DmFixBatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmGetBatchPicking.DmBatchPickingRequest;
import com.itechvision.ecrobo.pickman.Models.DmBatchPicking.DmGetBatchPicking.DmBatchPickingResponse;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.CheckInvoiceShipOrderResponse;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.SubmitMediacode.SubmitMediaReq;
import com.itechvision.ecrobo.pickman.Models.InvoiceShipping.SubmitMediacode.SubmitMediaResponse;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.InvoicePrint.InvoicePrintRequest;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.InvoicePrint.InvoicePrintResult;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.Invoice_orderIDResponse;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.Invoice_orderidReq;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.ShipCompany.InvoiceShipCompResult;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.ShipCompany.Invoice_ShipCompanyReq;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.SubmitInvoice.SubmitInvoiceReq;
import com.itechvision.ecrobo.pickman.Models.Invoice_Printer.SubmitInvoice.SubmitInvoiceResult;
import com.itechvision.ecrobo.pickman.Models.Logout.LogoutRequest;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode.LoopBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Barcode.LoopBarcodeResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderRequest;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetorderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.GetOrder.GetpendingorderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.LoopOrderReq;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.LoopOrderResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit.LoopSubmitResult;
import com.itechvision.ecrobo.pickman.Models.Loop_Shipping.Submit.SubmitReqLoop;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.ArrivalIDCheckRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Arrival_ID_Check.ArrivalIDCheckResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.BarcodeCheckRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.Barcode_Check.BarcodeCheckResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.ClearEventSubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.DirectArrival.DirectArrivalSubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.NormalArrivalSubmisson.ArrivalsubmissionRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.NormalArrivalSubmisson.ArrivalsubmissionResponse;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationRequest;
import com.itechvision.ecrobo.pickman.Models.MisukoshiArrival.StockClassification.StockClassificationResponse;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchiShipCompResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.KoguchiShipCompany.NKoguchi_ShipCompanyReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewKoguchi_orderIDResponse;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewKoguchi_orderidReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewkoguchiPrint.NewKoguchiPrintRequest;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.NewkoguchiPrint.NewkoguchiPrintResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi.SubmitKoguchiResult;
import com.itechvision.ecrobo.pickman.Models.NewKoguchi.SubmitNewKoguchi.SubmitnewKoguchiReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.OrderIdScanKoguchiReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.OrderIdScanKoguchiResponse;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize.SubmitKoguchiBoxsizeReq;
import com.itechvision.ecrobo.pickman.Models.NewKoguchiBoxSize.SubmitKoguchiboxsize.SubmitkoguchiboxsizeResponse;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.BarcodeCheckReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.BarcodeCheck.BarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck.LocationCheckReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.LocationCheck.LocationCheckResponse;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock.SubmitStockReq;
import com.itechvision.ecrobo.pickman.Models.NewMoveStock.SubmitStock.SubmitStockResponse;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.CheckPrinterResponse;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterCategoryResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.PrinterList.PrinterResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SavePrinter.SavePrinter_Request;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.SelectedprinterList.SelectedPrinterResult;
import com.itechvision.ecrobo.pickman.Models.NewPrinter.Template.PrinterTempResult;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetClassificationResult;
import com.itechvision.ecrobo.pickman.Models.NewReturnClassification.GetReturnClassificationReq;
import com.itechvision.ecrobo.pickman.Models.NewShipping.Boxsize.SetBoxSizeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckBarcode.CheckBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderNoProductResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.CheckOrder.CheckOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.FixOrder.FixOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.GetTotalOrders.GetOrderResponse;
import com.itechvision.ecrobo.pickman.Models.NewShipping.NotScanProductShippingRequest;
import com.itechvision.ecrobo.pickman.Models.NewShipping.ShippingRequest;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectReq;
import com.itechvision.ecrobo.pickman.Models.NyukaArrival.NyukaSelectResponse;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.Daimaru_OnetoOne_OrderIDReq;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.Daimaru_OnetoOne_OrderID_Res;
import com.itechvision.ecrobo.pickman.Models.OnetoOneSlip_printer.UpdateShip_request;

import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner.GetCSVSpinnerReq;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.CSV_spinner.GetCSVSpinnerResult;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.KoguchiReqst;
import com.itechvision.ecrobo.pickman.Models.PrintKoguchi.KoguchiResponse;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.ReturnStokeLotExpResult;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke.SubmitReturnStokeReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStock.SubmitReturnStoke.SubmitReturnStokeResult;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnBarcode.ReturnProductResponse;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationSubmitReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnLocationSubmit.ReturnLocationSubmitResponse;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId.ReturnOrderIDReq;
import com.itechvision.ecrobo.pickman.Models.ReturnStockBarcode.ReturnOrderId.ReturnOrderRespose;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingRequest;
import com.itechvision.ecrobo.pickman.Models.Settings.SettingResult;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode.CheckBarcodeShipReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckBarcode.CheckBarcodeShipResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckOrder.CheckOrderReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.CheckOrder.CheckShipOrderResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.GetShippingSettings.GetShippingSettingResponse;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.GetShippingSettings.GetShippingSettingsReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.PickBarcode.PickBarcodeReq;
import com.itechvision.ecrobo.pickman.Models.SimpleShippingModals.PickBarcode.PickBarcodeResponse;
import com.itechvision.ecrobo.pickman.Models.SubmitTime.TimeRequest;
import com.itechvision.ecrobo.pickman.Models.SubmitTime.TimeResponse;
import com.itechvision.ecrobo.pickman.Models.SyakkiID.SakiResponse;
import com.itechvision.ecrobo.pickman.Models.SyakkiID.SyakkiRequest;
import com.itechvision.ecrobo.pickman.Models.TasBatchInvoice.SubmitBatchInvoice;
import com.itechvision.ecrobo.pickman.Models.TasBatchInvoice.getTasinvoiceRespose;
import com.itechvision.ecrobo.pickman.Models.TotalArival.PickedResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.ReprintRequest;
import com.itechvision.ecrobo.pickman.Models.TotalArival.ReprintResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.StartArivalResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArivalReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArivalResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalListReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalSubmitReq;
import com.itechvision.ecrobo.pickman.Models.TotalArival.TotalArrivalSubmitResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.MylistResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.OtherListResponse;
import com.itechvision.ecrobo.pickman.Models.TotalArival.Update.UpdateProductRequest;
import com.itechvision.ecrobo.pickman.Models.TotalList.TotalListResponse;
import com.itechvision.ecrobo.pickman.Models.TotalShopListRequest;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.GetShippingCompanyRequest;
import com.itechvision.ecrobo.pickman.Models.TruckPicking.TruckpickingResult;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.AddArrival.AddArrivalReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.AddArrival.AddArrivalResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.GetArrivalReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrival.GetArrivalResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrivalOrderNo.GetArrivalOrderReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetArrivalOrderNo.GetArrivalOrderResponse;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival.GetArrivalBarcodeOrderNoReq;
import com.itechvision.ecrobo.pickman.Models.W6Arrival.GetOrderArrival.GetArrivalBarcodeOrderNoResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("api3/shop_list.php")
    Call<TotalListResponse> TotalList(@Body TotalShopListRequest req);

    @POST("api3/dolfin_nyuka_list.php")
    Call<TotalArivalResponse> TotalArrival(@Body TotalArrivalListReq req);

    @POST("api3/dolphin_arrival_confirm.php")
    Call<TotalArrivalSubmitResponse> TotalArrivalSubmit(@Body TotalArrivalSubmitReq req);

    @POST("api3/dolfin_nyuka_start.php")
    Call<StartArivalResponse> StartArival(@Body TotalArivalReq req);

    @POST("api3/dolfin_product_update.php")
    Call<ResponseBody> UpdateProduct(@Body UpdateProductRequest req);

    @POST("api3/dolfin_nyuka_picked.php")
    Call<PickedResponse> PickedProduct(@Body TotalArivalReq req);

    @POST("api3/dolfin_nyuka_printer.php")
    Call<ReprintResponse> Reprint(@Body ReprintRequest req);

    @POST("api3/dolfin_nyuka_list_my.php")
    Call<MylistResponse> MyList(@Body TotalArivalReq req);

    @POST("api3/dolfin_nyuka_list_other.php")
    Call<OtherListResponse> OtherList(@Body TotalArivalReq req);

    @POST("api3/dolfin_nyuka_time.php")
    Call<TimeResponse> ReturnTime(@Body TimeRequest req);

    @POST("api3/logout.php")
    Call<ResponseBody> Logout(@Body LogoutRequest req);

    //Tas Batch Invoice
    @POST("api3/tas_batch_invoice.php")
    Call<getTasinvoiceRespose> GetIvoicetas(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id, @Query("batch_id") String batch_id, @Query("order_id") String order_id, @Query("get_by") String getby);

    @POST("api3/tas_batch_invoice_status.php")
    Call<SubmitBatchInvoice> SubmitInvoiceTas(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id, @Query("batch_id") String batch_id, @Query("auto_scan") String auto_scan, @Query("order_id") String order_id, @Query("get_by") String getby);

    @POST("api3/tas_batch.php")
    Call<BatchpickResponse> BatchPick(@Body BatchPick_Request req);

    @POST("api3/tas_batch_status.php")
    Call<GetBatchStatusResult> GetBatchStatus(@Body GetBatchStatus_Req req);

    @POST("api3/total_batch.php")
    Call<BatchpickResponse> NewTasPicking(@Body BatchPick_Request req);

    @POST("api3/nyuka_lock.php")
    Call<NyukaSelectResponse> NyukaSelect(@Body NyukaSelectReq req);

    @POST("api3/tas_batch_barcode.php")
    Call<BarcodePickResponse> BatchPickBarcode(@Body BarcodePickRequest req);

    @POST("api3/tas_picking.php")
    Call<SubmitBarcodeResponse> SubmitBarcodeBatch(@Body SubmitBarcodeRequest req);

    @POST("api3/box_list.php")
    Call<ProductlistResponse> ProductCheck(@Body BarcodePickRequest req);

    @POST("api3/box_status.php")
    Call<BoxlistResponse> BoxStatus(@Body BoxlistRequest req);

    @POST("api3/total_picking_list.php")
    Call<TasBarcoderesult> GetTasBarcode(@Body TasPickBarcodeRequest req);

    @POST("api3/total_fixed_picking.php")
    Call<TasSubmitResult> TasBarcodeSubmit(@Body tasSubmitRequest req);

    //Skip api
    @POST("api3/total_skip.php")
    Call<TasSkipResult> SkipTas(@Query("admin_id") String adminId, @Query("authId") String authID,
    @Query("psh_id") String pshid, @Query("shop_id") String Shopid, @Query("batch_id") String batch_id,
       @Query("row_no") String row_no);

    //Clear B Total
    @POST("api3/total_clear.php")
    Call<BtotalClearResult> ClearBtotal(@Query("admin_id") String adminId, @Query("authId") String authID,
    @Query("shop_id") String Shopid, @Query("batch_id") String batch_id, @Query("create_date") String create_date);

    //Printer Work
    @POST("api3/ap_form_category.php")
    Call<PrinterCategoryResult> PrinterCategoty(@Query("admin_id") String admin_id, @Query("authId") String authId);

    @POST("api3/ap_form.php")
    Call<PrinterTempResult> PrinterTemplate(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("ap_form_category_id") String ap_form_category_id, @Query("shop_id") String shop_id);

    @POST("api3/ap_printer_list.php")
    Call<PrinterResult> Printerlist(@Query("admin_id") String admin_id, @Query("authId") String authId);

    @POST("api3/save_form_printer.php")
    Call<ResponseBody> SavePrinter(@Body SavePrinter_Request Req);

    @POST("api3/ap_form_printer.php")
    Call<CheckPrinterResponse> CheckPrinter(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id, @Query("ap_form_id") String Templateid);

    @POST("api3/ap_form_printer_list.php")
    Call<SelectedPrinterResult> SelectedPrinter_list(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id);

    @POST("api3/ap_form_printer_delete.php")
    Call<ResponseBody> Delete_SelectedPrinter(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id, @Query("id") String id);

    //MEnu Settings Data
    @POST("api3/menu_settings.php")
    Call<ResponseBody> PostSettingStatus(@Body SettingRequest req);

    @POST("api3/get_menu_settings.php")
    Call<SettingResult> GetSettings(@Query("admin_id") String admin_id, @Query("authId") String authId, @Query("shop_id") String shop_id);

    // NewShipping Data
    @POST("api3/order_cat_initial_call.php")
    Call<GetOrderResponse> GetOrderNo(@Body ShippingRequest req);

    @POST("api3/order_cat_sort.php")
    Call<CheckOrderResponse> GetOrder(@Body ShippingRequest req);

    //if not scan Products
    @POST("api3/order_complete.php")
    Call<CheckOrderNoProductResponse> GetOrderNoProduct(@Body NotScanProductShippingRequest req);

    @POST("api3/order_user_pikking.php")
    Call<CheckBarcodeResponse> CheckBarcode(@Body ShippingRequest req);

    @POST("api3/fixed_cat_auto_complete.php")
    Call<FixOrderResponse> FixOrder(@Body ShippingRequest req);

    @POST("api3/fixed_cat_box_piking.php")
    Call<SetBoxSizeResponse> SetboxSize(@Body ShippingRequest req);

    //NewTastBatch
    @POST("api3/tas_order.php")
    Call<BatchpickResponse> CheckTasOrder(@Body CheckTasorderRequest req);

    @POST("api3/tas_working.php")
    Call<TasworkingRespose> Tasworking(@Body TasworkingRequest req);

    //Truck Pick
    @POST("api3/dbatch_truck_picking_new.php")
    Call<TruckpickingResult> GetShippingCompany(@Body GetShippingCompanyRequest req);

    //NEw Arrival Weight
    @POST("api3/product_update.php")
    Call<ResponseBody> ArrivalWeight(@Body ArrivalWeightRequest req);

    // ReturnStockBarcode
    @POST("api3/get_returned_order.php")
    Call<ReturnOrderRespose> GetReturnOrder(@Body ReturnOrderIDReq req);

    @POST("api3/get_returned_product.php")
    Call<ReturnProductResponse> GetReturnProduct(@Body ReturnProductReq req);

    @POST("api3/move_returned_product.php")
    Call<ReturnLocationSubmitResponse> GetReturnLocationSubmit(@Body ReturnLocationSubmitReq req);

    @POST("api3/list_stock_ids.php")
    Call<SakiResponse> SakiyakiIds(@Body SyakkiRequest req);

    // PrintKoguchi
    @POST("api3/cover_print.php")
    Call<KoguchiResponse> PrintKoguchiAPI(@Body KoguchiReqst req);


    // Return Classification
    @POST("api3/shop_return_class.php")
    Call<GetClassificationResult> GetReturnClassification(@Body GetReturnClassificationReq req);

    @POST("api3/edi2_list.php")
    Call<GetCSVSpinnerResult> GetCSVSpinnerAPI(@Body GetCSVSpinnerReq req);

    //---------------------------New Koguchi Screen--------------------//

    //New Koguchi
    @POST("api3/get_order_data.php")
    Call<NewKoguchi_orderIDResponse> NewKoguchiOrderID(@Body NewKoguchi_orderidReq req);

    //new koguchi shipping company
    @POST("api3/get_shipping_data.php")
    Call<NKoguchiShipCompResult> NewKoguchiShipCompany(@Body NKoguchi_ShipCompanyReq req);

    //new koguchi Submit or update
    @POST("api3/order_tracking_update.php")
    Call<SubmitKoguchiResult> NewKoguchiSubmit(@Body SubmitnewKoguchiReq req);

    //new koguchi print
    @POST("api3/get_order_print.php")
    Call<NewkoguchiPrintResult> NewKoguchiPrint(@Body NewKoguchiPrintRequest req);

    //new KougiBoxSize Shipp Specification
    @POST("api3/track_scan.php")
    Call<OrderIdScanKoguchiResponse> NewKoguchiOrderIDScan(@Body OrderIdScanKoguchiReq req);

    //new KougiBoxSize submit Shipp Specification
    @POST("api3/submission_boxsize.php")
    Call<SubmitkoguchiboxsizeResponse> SubmitKoguchiBoxsize(@Body SubmitKoguchiBoxsizeReq req);

    //---------------------------Arrival_ID_Activity-----------------------//

    //ArrivalId check

    @POST("api3/get_arrival_order.php")
    Call<ArrivalIDCheckResponse> ArrivalIDCheck(@Body ArrivalIDCheckRequest req);

    //Barcode check
    @POST("api3/get_arrival_code.php")
    Call<BarcodeCheckResponse> BarcodeCheckAPI(@Body BarcodeCheckRequest req);

    //Normal and MiddleArrival Submission
    @POST("api3/confirm_arrival_order.php")
    Call<ArrivalsubmissionResponse> ArrivalSubmissionAPI(@Body ArrivalsubmissionRequest req);

    //Normal and MiddleArrival Submission
    @POST("api3/move_arrival_order.php")
    Call<ArrivalsubmissionResponse> DirectArrivalSubmissionAPI(@Body DirectArrivalSubmissionRequest req);

    //Clear event
    @POST("api3/switch_arrival_user.php")
    Call<ResponseBody> ClearEventAPI(@Body ClearEventSubmissionRequest req);

    //StockClassification List
    @POST("api3/stock_types.php")
    Call<StockClassificationResponse> StockClassificationAPI(@Body StockClassificationRequest req);



    //Location List Direct Arrival
    @POST("api3/list_stock.php")
    Call<DirArrivalLoctionResult> LocationList(@Body LocationReq req);




     //--------------------------onetoone_Slip Printer--------------------//

    @POST("api3/daimatsu_update_shipping_data.php")
    Call<ResponseBody> One_to_One_UpdateShip(@Body UpdateShip_request req);

    @POST("api3/daimatsu_get_order.php")
    Call<Daimaru_OnetoOne_OrderID_Res> GetORderID(@Body Daimaru_OnetoOne_OrderIDReq req);

    @POST("api3/daimatsu_shipping_data.php")
    Call<NKoguchiShipCompResult> One_to_One_SlipPrintCompanies(@Body NKoguchi_ShipCompanyReq req);

 //------------------daimaru Shipping----------------//
 // NewShipping Data
      @POST("api3/daimatsu_order_cat_initial_call.php")
   Call<Daimaru_GetOrderRespose> DaimaruGetOrderNo(@Body DaimaruShippingRequest req);

    @POST("api3/daimatsu_order_cat_sort.php")
    Call<CheckOrderResponse> DaimaruGetOrder(@Body DaimaruShippingRequest req);

    @POST("api3/daimatsu_order_user_pikking.php")
    Call<CheckBarcodeResponse> DaimaruCheckBarcode(@Body DaimaruShippingRequest req);

    @POST("api3/daimatsu_fixed_cat_auto_complete.php")
    Call<FixOrderResponse> DaimaruFixOrder(@Body DaimaruShippingRequest req);

    @POST("api3/daimatsu_fixed_cat_box_piking.php")
    Call<SetBoxSizeResponse> DaimaruSetboxSize(@Body DaimaruShippingRequest req);

    //if not scan Products
    @POST("api3/daimatsu_order_complete.php")
    Call<CheckOrderNoProductResponse> DaimaruGetOrderNoProduct(@Body NotScanProductShippingRequest req);

    //Update Koguchi Ship
    @POST("api3/daimatsu_update_koguchi_shipping_data.php")
    Call<ResponseBody> DaimaruUpdateKoguchi(@Body DaimaruKoguchiShipUpdatereq req);


    //------------------------Arrival Server 6-------------------

    //GetArrivalOrderNo
    @POST("api3/daimatsu_arrival_orderno.php")
    Call<GetArrivalOrderResponse> GetArrivalOrderNoAPI(@Body GetArrivalOrderReq req);

    //GetArrival
    @POST("api3/daimatsu_arrival_barcode.php")
    Call<GetArrivalResponse> GetArrivalAPI(@Body GetArrivalReq req);

    //GetArrivalBarcodeOrderNo
    @POST("api3/daimatsu_arrival_code_order.php")
    Call<GetArrivalBarcodeOrderNoResponse> GetArrivalBarcodeOrderNoAPI(@Body GetArrivalBarcodeOrderNoReq req);

    //AddArrival
    @POST("api3/daimatsu_add_arrival.php")
    Call<AddArrivalResponse> AddArrivalAPI(@Body AddArrivalReq req);

    //-------------------------------BatchPickingW6 ------------------------//

    //BatchListCall
    @POST("api3/daimatsu_total_batch.php")
    Call<BatchListResponse> BatchListAPI(@Body BatchListRequest req);

    //BoxBatch
    @POST("api3/daimatsu_tas_batch_invoice.php")
    Call<BoxBatchResponse> GetboxBatch(@Body BoxBatchRequest req );


    //BoxBatchSubmit
    @POST("api3/daimatsu_tas_batch_invoice_status.php")
    Call<BoxBatchSubmitResponse> BoxBatchSubmit(@Body BoxBatchSubmitReq req );

    //GetBatchPicking Request
    @POST("api3/daimatsu_get_order_detail.php")
    Call<BatchPickingResponse> GetBatchPicking(@Body BatchPickingRequest req );

    //FixBatchPicking Request
    @POST("api3/daimatsu_dbatch_truck_picking.php")
    Call<FixBatchPickingResponse> FixBatchPicking(@Body FixBatchPickingRequest req );


    //----------------------Retrun Stock -----------------
    //api3/get_stock_info.php

    @POST("api3/get_stock_info.php")
    Call<ReturnStokeLotExpResult> GETLOTEXP(@Body ReturnStokeLotExpReq req );


    @POST("api3/order_return.php")
    Call<SubmitReturnStokeResult> SubmitRetunStoke(@Body SubmitReturnStokeReq req );



    //------------------------Simple Shipping Activity---------------------//

    //GetSettings
    @POST("api3/shop_settings.php")
    Call<GetShippingSettingResponse> GetSettingsAPI(@Body GetShippingSettingsReq req);

    //CheckOrderID
    @POST("api3/ship_get_order.php")
    Call<CheckShipOrderResponse> CheckOrderShipping(@Body CheckOrderReq req);

    //CheckBarcodeShip
    @POST("api3/ship_get_barcode.php")
    Call<CheckBarcodeShipResponse> CheckBarcodeShipping(@Body CheckBarcodeShipReq req);

    //PickBarcode
    @POST("api3/ship_barcode_pick.php")
    Call<PickBarcodeResponse> PickBarcodeAPI(@Body PickBarcodeReq req);


    //---------------------Shipping loop -----------------------------//

    @POST("api3/loop_order_scan.php")
    Call<LoopOrderResult> LoopShipingOrderAPI(@Body LoopOrderReq req);

    @POST("api3/loop_barcode_scan.php")
    Call<LoopBarcodeResult> LoopShipingBarcodeAPI(@Body LoopBarcodeReq req);

    @POST("api3/loop_serialno.php")
    Call<LoopSubmitResult> SubmitLoopShipingAPI(@Body SubmitReqLoop req);

    @POST("api3/loop_get_order.php")
    Call<GetorderResult> GETLoopOrder(@Body GetorderRequest req);


    @POST("api3/loop_pending_barcode.php")
    Call<GetpendingorderResult> GETLoopPendingOrder(@Body LoopOrderReq req);


    //---------------------------------BoxCashRegister Activity APIs--------------------------------------//
    //OrderID
    @POST("api3/order_detail.php")
    Call<BoxCashOrderIDResp> GetBoxOrderID( @Body BoxCashOrderIDRequest req);

    //BoxList
    @POST("api3/ems_box_list.php")
    Call<BoxListResp> GetBoxList(@Body BoxListRequest req);

    //Fix Request( es vch polithine valu bhjni a)
    @POST("api3/ship_pick.php")
    Call<PickProductRes> SetPickProduct( @Body PickProductReq req);

    //get Box no
    @POST("api3/ship_box_no.php")
    Call<GetBoxNoResponse> GetBoxNo(@Body NextBoxReq req);

    //Get PackingList
    @POST("api3/ship_order_box.php")
    Call<GetPackingListResponse> GetPackingListAPI(@Body BoxCashOrderIDRequest req);

    //Recreate Box API
    @POST("api3/ship_recreate.php")
    Call<BoxRecreateResponse> GetBoxRecreateAPI( @Body BoxRecreateReq req);

    //Box Product click list
    @POST("api3/ship_box_detail.php")
    Call<BoxProductclickListResponse> boxproductlistclick(@Body BoxProductClickRequest req);


    //--------------------------CustomerID Arrival Screen------------------//

    //Check Customer ID
    @POST("api3/wms_customer_check.php")
    Call<CustomerCheckResponse> CheckCustomerID(@Body CustomerCheckReq req);

    //Check Barcode
    @POST("api3/get_arrival.php")
    Call<CustomerBarcodeResponse> CheckBarcodeArrival(@Body CustomerBarcodeReq req);

    //Check Barcode
    @POST("api3/wms_order_arrival.php")
    Call<SubmitArrivalResponse> SubmitArrival(@Body SubmitArrivalReq req);

    //--------------------------New MoveStock Screen------------------//

    //Check Location
    @POST("api3/move_stock_location.php")
    Call<LocationCheckResponse>LocationCheck(@Body LocationCheckReq req);

    //Check Barcode
    @POST("api3/move_stock_product.php")
    Call<BarcodeResponse> BarcodeCheck(@Body BarcodeCheckReq req);

    //Submit Stock
    @POST("api3/move_stock_submission.php")
    Call<SubmitStockResponse> SubmitStockAPI(@Body SubmitStockReq req);

    //--------------------------Invoice Shipping Screen------------------//

    //ORDERID API CALL
    @POST("api3/get_mediacode.php")
    Call<CheckInvoiceShipOrderResponse>OrderIDCheck(@Body com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.CheckOrderReq req);

    //Check OrderId Check
    @POST("api3/update_mediacode.php")
    Call<SubmitMediaResponse>TrackSubmitAPI(@Body SubmitMediaReq req);

    //-------------------------------DmBatchPicking ------------------------//

    //BatchListCall
    @POST("api3/pickman_total_batch.php")
    Call<DmBatchListResponse> DmBatchListAPI(@Body DmBatchListRequest req);


    //BoxBatch
    @POST("api3/pickman_tas_batch_invoice.php")
    Call<DmBoxBatchResponse> DmGetboxBatch(@Body DmBoxBatchRequest req );


    //BoxBatchSubmit
    @POST("api3/pickman_tas_batch_invoice_status.php")
    Call<DmBoxBatchSubmitResponse> DmBoxBatchSubmit(@Body DmBoxBatchSubmitReq req );

    //GetBatchPicking Request
    @POST("api3/pickman_get_order_detail.php")
    Call<DmBatchPickingResponse> DmGetBatchPicking(@Body DmBatchPickingRequest req );

    //FixBatchPicking Request
    @POST("api3/pickman_dbatch_truck_picking.php")
    Call<DmFixBatchPickingResponse> DmFixBatchPicking(@Body DmFixBatchPickingRequest req );

    //---------------------------Invoice Print Screen-------------------//

    //New Koguchi
    @POST("api3/invoice_data.php")
    Call<Invoice_orderIDResponse> InvoiceOrderID(@Body Invoice_orderidReq req);


    //new koguchi shipping company
    @POST("api3/invoice_shipping_data.php")
    Call<InvoiceShipCompResult> InvoiceShipCompany(@Body Invoice_ShipCompanyReq req);


    //Invoice Submit or update
    @POST("api3/invoice_update.php")
    Call<SubmitInvoiceResult> InvoiceSubmit(@Body SubmitInvoiceReq req);

    //Invoice print
    @POST("api3/invoice_print.php")
    Call<InvoicePrintResult> InvoicePrint(@Body InvoicePrintRequest req);




}


