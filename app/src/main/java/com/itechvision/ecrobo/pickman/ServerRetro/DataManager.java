package com.itechvision.ecrobo.pickman.ServerRetro;

import android.util.Log;

import com.google.gson.JsonIOException;
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
import com.itechvision.ecrobo.pickman.Models.CustomerArrival.SubmitArrival.SubmitArrivalReq;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {

    ApiInterface apiService;
    //=== login
    public interface TotalListcallback {
        void onSucess(int status, TotalListResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  TotalList(TotalShopListRequest req, final TotalListcallback callback){
        apiService.TotalList(req).enqueue(new Callback<TotalListResponse>() {
            @Override
            public void onResponse(Call<TotalListResponse> call, Response<TotalListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TotalListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Total Arrival

    public interface TotalArrivalcallback {
        void onSucess(int status, TotalArivalResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  TotalArrival(TotalArrivalListReq req, final TotalArrivalcallback callback){
        apiService.TotalArrival(req).enqueue(new Callback<TotalArivalResponse>() {
            @Override
            public void onResponse(Call<TotalArivalResponse> call, Response<TotalArivalResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TotalArivalResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //DolphinSubmission API

    public interface TotalArrivalSubmissioncallback {
        void onSucess(int status, TotalArrivalSubmitResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  TotalArrivalSubmit(TotalArrivalSubmitReq req, final TotalArrivalSubmissioncallback callback){
        apiService.TotalArrivalSubmit(req).enqueue(new Callback<TotalArrivalSubmitResponse>() {
            @Override
            public void onResponse(Call<TotalArrivalSubmitResponse> call, Response<TotalArrivalSubmitResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TotalArrivalSubmitResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //PickedProduct

    public interface PickedProductcallback {
        void onSucess(int status, PickedResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  PickedProduct(TotalArivalReq req, final PickedProductcallback callback){
        apiService.PickedProduct(req).enqueue(new Callback<PickedResponse>() {
            @Override
            public void onResponse(Call<PickedResponse> call, Response<PickedResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<PickedResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //MYList
    public interface Mylistcallback {
        void onSucess(int status, MylistResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  MyList(TotalArivalReq req, final Mylistcallback callback){
        apiService.MyList(req).enqueue(new Callback<MylistResponse>() {
            @Override
            public void onResponse(Call<MylistResponse> call, Response<MylistResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<MylistResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //OtherList
    public interface OtherListcallback {
        void onSucess(int status, OtherListResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  OtherList(TotalArivalReq req, final OtherListcallback callback){
        apiService.OtherList(req).enqueue(new Callback<OtherListResponse>() {
            @Override
            public void onResponse(Call<OtherListResponse> call, Response<OtherListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<OtherListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Start Arrival
    public interface Startcallback {
        void onSucess(int status, StartArivalResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  StartArival(TotalArivalReq req, final Startcallback callback){
        apiService.StartArival(req).enqueue(new Callback<StartArivalResponse>() {
            @Override
            public void onResponse(Call<StartArivalResponse> call, Response<StartArivalResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<StartArivalResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Update Arriva
    public interface Updatecallback {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  UpdateProduct(UpdateProductRequest req, final Updatecallback callback){
        apiService.UpdateProduct(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //submit time
    public interface Timecallback {
        void onSucess(int status, TimeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  ReturnTime(TimeRequest req, final Timecallback callback){
        apiService.ReturnTime(req).enqueue(new Callback<TimeResponse>() {
            @Override
            public void onResponse(Call<TimeResponse> call, Response<TimeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TimeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Logout
    public interface Logoutcallback {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  Logout(LogoutRequest req, final Logoutcallback callback){
        apiService.Logout(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //GetOrderbatchpick
    public interface GetOrderBatchcall {
        void onSucess(int status, BatchpickResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  BatchPick(BatchPick_Request req, final GetOrderBatchcall callback){
        apiService.BatchPick(req).enqueue(new Callback<BatchpickResponse>() {
            @Override
            public void onResponse(Call<BatchpickResponse> call, Response<BatchpickResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BatchpickResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Tas Batch invoice
    public interface GetIvoicetascall {
        void onSucess(int status, getTasinvoiceRespose message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetIvoicetas(String admin_id,String authId,String shop_id,String batch_id,String order_id,String getby, final GetIvoicetascall callback){
        apiService.GetIvoicetas(admin_id,authId,shop_id,batch_id,order_id,getby).enqueue(new Callback<getTasinvoiceRespose>() {
            @Override
            public void onResponse(Call<getTasinvoiceRespose> call, Response<getTasinvoiceRespose> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<getTasinvoiceRespose> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Submit Batch invoice
    public interface SubmitInvoiceTascall {
        void onSucess(int status, SubmitBatchInvoice message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SubmitInvoiceTas(String admin_id,String authId,String shop_id,String batch_id,String Scan,String Orderid,String getby, final SubmitInvoiceTascall callback){
        apiService.SubmitInvoiceTas(admin_id,authId,shop_id,batch_id,Scan,Orderid,getby).enqueue(new Callback<SubmitBatchInvoice>() {
            @Override
            public void onResponse(Call<SubmitBatchInvoice> call, Response<SubmitBatchInvoice> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SubmitBatchInvoice> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Skip Tas
    public interface SkipTascall {
        void onSucess(int status, TasSkipResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SkipTas(String admin_id,String authId,String pshid,String shop_id,String batchid,String row_no, final SkipTascall callback){
        apiService.SkipTas(admin_id,authId,pshid,shop_id,batchid,row_no).enqueue(new Callback<TasSkipResult>() {
            @Override
            public void onResponse(Call<TasSkipResult> call, Response<TasSkipResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TasSkipResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

//Clear Btotal

    //Skip Tas
    public interface CleatBatchcall {
        void onSucess(int status, BtotalClearResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  ClearBtotal(String admin_id,String authId, String shop_id,String batchid,String createdate, final CleatBatchcall callback){
        apiService.ClearBtotal(admin_id,authId, shop_id,batchid,createdate).enqueue(new Callback<BtotalClearResult>() {
            @Override
            public void onResponse(Call<BtotalClearResult> call, Response<BtotalClearResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BtotalClearResult> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    public interface GetNewTasPickingcall {
        void onSucess(int status, BatchpickResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewTasPicking(BatchPick_Request req, final GetNewTasPickingcall callback){
        apiService.NewTasPicking(req).enqueue(new Callback<BatchpickResponse>() {
            @Override
            public void onResponse(Call<BatchpickResponse> call, Response<BatchpickResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BatchpickResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Nyukaselect
    //=== login
    public interface Nyukacallback {
        void onSucess(int status, NyukaSelectResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NyukaSelect(NyukaSelectReq req, final Nyukacallback callback){
        apiService.NyukaSelect(req).enqueue(new Callback<NyukaSelectResponse>() {
            @Override
            public void onResponse(Call<NyukaSelectResponse> call, Response<NyukaSelectResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<NyukaSelectResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Barcodescan batch
    public interface barcodeBatchcall {
        void onSucess(int status, BarcodePickResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  BatchPickBarcode(BarcodePickRequest req, final barcodeBatchcall callback){
        apiService.BatchPickBarcode(req).enqueue(new Callback<BarcodePickResponse>() {
            @Override
            public void onResponse(Call<BarcodePickResponse> call, Response<BarcodePickResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BarcodePickResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //submitbarcodebatch
    public interface SubmitBarcodecall {
        void onSucess(int status, SubmitBarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SubmitBarcodeBatch(SubmitBarcodeRequest req, final SubmitBarcodecall callback){
        apiService.SubmitBarcodeBatch(req).enqueue(new Callback<SubmitBarcodeResponse>() {
            @Override
            public void onResponse(Call<SubmitBarcodeResponse> call, Response<SubmitBarcodeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SubmitBarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //GetProduct List
    public interface GetProductcall {
        void onSucess(int status, ProductlistResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  ProductCheck(BarcodePickRequest req, final GetProductcall callback){
        apiService.ProductCheck(req).enqueue(new Callback<ProductlistResponse>() {
            @Override
            public void onResponse(Call<ProductlistResponse> call, Response<ProductlistResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ProductlistResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Box Status
    public interface GetBoxststuscall {
        void onSucess(int status, BoxlistResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  BoxStatus(BoxlistRequest req, final GetBoxststuscall callback){
        apiService.BoxStatus(req).enqueue(new Callback<BoxlistResponse>() {
            @Override
            public void onResponse(Call<BoxlistResponse> call, Response<BoxlistResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BoxlistResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Tas Barcode
    public interface GetTasBarcodecall {
        void onSucess(int status, TasBarcoderesult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetTasBarcode(TasPickBarcodeRequest req, final GetTasBarcodecall callback){
        apiService.GetTasBarcode(req).enqueue(new Callback<TasBarcoderesult>() {
            @Override
            public void onResponse(Call<TasBarcoderesult> call, Response<TasBarcoderesult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TasBarcoderesult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //TasSubmitBarcode

    public interface TasSubmitcall {
        void onSucess(int status, TasSubmitResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  TasBarcodeSubmit(tasSubmitRequest req, final TasSubmitcall callback){
        apiService.TasBarcodeSubmit(req).enqueue(new Callback<TasSubmitResult>() {
            @Override
            public void onResponse(Call<TasSubmitResult> call, Response<TasSubmitResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TasSubmitResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //GetBatch Status
    public interface GetBAtchStatuscall {
        void onSucess(int status, GetBatchStatusResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }


    public void  GetBatchStatus(GetBatchStatus_Req req, final GetBAtchStatuscall callback){
        apiService.GetBatchStatus(req).enqueue(new Callback<GetBatchStatusResult>() {
            @Override
            public void onResponse(Call<GetBatchStatusResult> call, Response<GetBatchStatusResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GetBatchStatusResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //GetPrinter Categories
    public interface GetPinterCatcall {
        void onSucess(int status, PrinterCategoryResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  PrinterCategoty(String admin_id,String authid, final GetPinterCatcall callback){
        apiService.PrinterCategoty(admin_id,authid).enqueue(new Callback<PrinterCategoryResult>() {
            @Override
            public void onResponse(Call<PrinterCategoryResult> call, Response<PrinterCategoryResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<PrinterCategoryResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //GetPrinter PrinterTemplate
    public interface PrinterTemplatecall {
        void onSucess(int status, PrinterTempResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  PrinterTemplate(String admin_id,String authid,String ap_form_category_id,String shop_id, final PrinterTemplatecall callback){
        apiService.PrinterTemplate(admin_id,authid,ap_form_category_id,shop_id).enqueue(new Callback<PrinterTempResult>() {
            @Override
            public void onResponse(Call<PrinterTempResult> call, Response<PrinterTempResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<PrinterTempResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Check Printer

    public interface CheckPrintercall {
        void onSucess(int status, CheckPrinterResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  CheckPrinter(String admin_id,String authid,String shop_id,String tempid, final CheckPrintercall callback){
        apiService.CheckPrinter(admin_id,authid,shop_id,tempid).enqueue(new Callback<CheckPrinterResponse>() {
            @Override
            public void onResponse(Call<CheckPrinterResponse> call, Response<CheckPrinterResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckPrinterResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Selected Printerlist
    public interface SelectedPrinter_listcall {
        void onSucess(int status, SelectedPrinterResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SelectedPrinter_list(String admin_id,String authid,String shop_id , final SelectedPrinter_listcall callback){
        apiService.SelectedPrinter_list(admin_id,authid,shop_id).enqueue(new Callback<SelectedPrinterResult>() {
            @Override
            public void onResponse(Call<SelectedPrinterResult> call, Response<SelectedPrinterResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SelectedPrinterResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Get Settings
    public interface GetSettingscall {
        void onSucess(int status, SettingResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetSettings(String admin_id,String authid,String shop_id , final GetSettingscall callback){
        apiService.GetSettings(admin_id,authid,shop_id).enqueue(new Callback<SettingResult>() {
            @Override
            public void onResponse(Call<SettingResult> call, Response<SettingResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SettingResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Selected Delte Printerlist
    public interface Delete_SelectedPrintercall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  Delete_SelectedPrinter(String admin_id,String authid,String shop_id ,String id, final Delete_SelectedPrintercall callback){
        apiService.Delete_SelectedPrinter(admin_id,authid,shop_id,id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Post Setting
    public interface Postsettingcall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  PostSettingStatus(SettingRequest req, final Postsettingcall callback){
        apiService.PostSettingStatus(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //GetAll Printer
    public interface Printerlistcall {
        void onSucess(int status, PrinterResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  Printerlist(String admin_id,String authid,final Printerlistcall callback){
        apiService.Printerlist(admin_id,authid ).enqueue(new Callback<PrinterResult>() {
            @Override
            public void onResponse(Call<PrinterResult> call, Response<PrinterResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<PrinterResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Save Printer
    public interface SavePrintercall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SavePrinter(SavePrinter_Request req, final SavePrintercall callback){
        apiService.SavePrinter(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //REprint

    public interface Reprintcallback {
        void onSucess(int status, ReprintResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }


    public void Reprint(ReprintRequest req, final Reprintcallback callback){

        apiService.Reprint(req).enqueue(new Callback<ReprintResponse>() {
            @Override
            public void onResponse(Call<ReprintResponse> call, Response<ReprintResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReprintResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });

    }

//NEW SHIPPING

    //GetTotalNo. of Orders
    public interface GetOrderNocallback {
        void onSucess(int status, GetOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void GetOrderNo(ShippingRequest req, final GetOrderNocallback callback){

        apiService.GetOrderNo(req).enqueue(new Callback<GetOrderResponse>()  {
            @Override
            public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });

    }

    //CHECK ORDERID
    public interface GetOrderCallback {
        void onSucess(int status, CheckOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void GetOrder(ShippingRequest req, final GetOrderCallback callback){

        apiService.GetOrder(req).enqueue(new Callback<CheckOrderResponse>()  {
            @Override
            public void onResponse(Call<CheckOrderResponse> call, Response<CheckOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //CHECK ORDERID FOR SCAN PRODUCTS
    public interface GetOrderNoProductCallback {
        void onSucess(int status, CheckOrderNoProductResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void GetOrderNoProduct(NotScanProductShippingRequest req, final GetOrderNoProductCallback callback){

        apiService.GetOrderNoProduct(req).enqueue(new Callback<CheckOrderNoProductResponse>()  {
            @Override
            public void onResponse(Call<CheckOrderNoProductResponse> call, Response<CheckOrderNoProductResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckOrderNoProductResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //CHECK BARCODE
    public interface CheckBarcodeCallback {
        void onSucess(int status, CheckBarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void CheckBarcode( ShippingRequest req, final CheckBarcodeCallback callback){

        apiService.CheckBarcode(req).enqueue(new Callback<CheckBarcodeResponse>()  {
            @Override
            public void onResponse(Call<CheckBarcodeResponse> call, Response<CheckBarcodeResponse> response) {
                int statusCode = response.code();
                Log.e(">>>>>>>>>>","<<<<<<<<<<<"+statusCode);
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckBarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //SEND QTY
    public interface FixOrderCallback {
        void onSucess(int status, FixOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void FixOrder(ShippingRequest req, final FixOrderCallback callback){

        apiService.FixOrder(req).enqueue(new Callback<FixOrderResponse>()  {
            @Override
            public void onResponse(Call<FixOrderResponse> call, Response<FixOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<FixOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //SEND BOX SIZe
    public interface SetboxSizeCallback {
        void onSucess(int status, SetBoxSizeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void SetboxSize(ShippingRequest req, final SetboxSizeCallback callback){

        apiService.SetboxSize(req).enqueue(new Callback<SetBoxSizeResponse>()  {
            @Override
            public void onResponse(Call<SetBoxSizeResponse> call, Response<SetBoxSizeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SetBoxSizeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //CheckTasorder
    public interface GetCheckTasordercall {
        void onSucess(int status, BatchpickResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  CheckTasOrder(CheckTasorderRequest req, final GetCheckTasordercall callback){
        apiService.CheckTasOrder(req).enqueue(new Callback<BatchpickResponse>() {
            @Override
            public void onResponse(Call<BatchpickResponse> call, Response<BatchpickResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BatchpickResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Tasworking
    public interface GetTasworkingrcall {
        void onSucess(int status, TasworkingRespose message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  Tasworking(TasworkingRequest req, final GetTasworkingrcall callback){
        apiService.Tasworking(req).enqueue(new Callback<TasworkingRespose>() {
            @Override
            public void onResponse(Call<TasworkingRespose> call, Response<TasworkingRespose> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TasworkingRespose> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Truck Picking
    public interface GetShippingCompanycall {
        void onSucess(int status, TruckpickingResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetShippingCompany(GetShippingCompanyRequest req, final GetShippingCompanycall callback){
        apiService.GetShippingCompany(req).enqueue(new Callback<TruckpickingResult>() {
            @Override
            public void onResponse(Call<TruckpickingResult> call, Response<TruckpickingResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<TruckpickingResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Truck Picking
    public interface ArrivalWeightcall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  ArrivalWeight(ArrivalWeightRequest req, final ArrivalWeightcall callback){
        apiService.ArrivalWeight(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

//ReturnStockBarcodeActivity




    //ReturnOrderID
    public interface GetReturnOrderIDcall {

        void onSucess(int status, ReturnOrderRespose message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetReturnOrder(ReturnOrderIDReq req, final GetReturnOrderIDcall callback){
        apiService.GetReturnOrder(req).enqueue(new Callback<ReturnOrderRespose>() {
            @Override
            public void onResponse(Call<ReturnOrderRespose> call, Response<ReturnOrderRespose> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReturnOrderRespose> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //ReturnProduct
    public interface GetReturnProductcall {

        void onSucess(int status, ReturnProductResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetReturnProduct(ReturnProductReq req, final GetReturnProductcall callback){
        apiService.GetReturnProduct(req).enqueue(new Callback<ReturnProductResponse>() {
            @Override
            public void onResponse(Call<ReturnProductResponse> call, Response<ReturnProductResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReturnProductResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //ReturnLocationSubmit
    public interface GetReturnLocationSubmitcall {

        void onSucess(int status, ReturnLocationSubmitResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetReturnLocationSubmit(ReturnLocationSubmitReq req, final GetReturnLocationSubmitcall callback){
        apiService.GetReturnLocationSubmit(req).enqueue(new Callback<ReturnLocationSubmitResponse>() {
            @Override
            public void onResponse(Call<ReturnLocationSubmitResponse> call, Response<ReturnLocationSubmitResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReturnLocationSubmitResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }
    //Saki IDs
    public interface GetSakiIDcall {

        void onSucess(int status, SakiResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SakiyakiIds(SyakkiRequest req, final GetSakiIDcall callback){
        apiService.SakiyakiIds(req).enqueue(new Callback<SakiResponse>() {
            @Override
            public void onResponse(Call<SakiResponse> call, Response<SakiResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SakiResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }
//PrintKoguchiActivity
    // PrintKoguchi

    public interface PrintKoguchiCallback {
        void onSucess(int status, KoguchiResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  PrintKoguchiAPI(KoguchiReqst req, final PrintKoguchiCallback callback){
        apiService.PrintKoguchiAPI(req).enqueue(new Callback<KoguchiResponse>() {
            @Override
            public void onResponse(Call<KoguchiResponse> call, Response<KoguchiResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<KoguchiResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    public interface GetReturnClassificationCallback {
        void onSucess(int status, GetClassificationResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetReturnClassification(GetReturnClassificationReq req, final GetReturnClassificationCallback callback){
        apiService.GetReturnClassification(req).enqueue(new Callback<GetClassificationResult>() {
            @Override
            public void onResponse(Call<GetClassificationResult> call, Response<GetClassificationResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GetClassificationResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    // Get CSV Spinner List
    public interface GetCSVSpinnerCallback {
        void onSucess(int status, GetCSVSpinnerResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetCSVSpinnerAPI(GetCSVSpinnerReq req, final GetCSVSpinnerCallback callback){
        apiService.GetCSVSpinnerAPI(req).enqueue(new Callback<GetCSVSpinnerResult>() {
            @Override
            public void onResponse(Call<GetCSVSpinnerResult> call, Response<GetCSVSpinnerResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GetCSVSpinnerResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }



    // New Koguchi OrderID Scan

    public interface NewKoguchiOrderIDCallback {
        void onSucess(int status, NewKoguchi_orderIDResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewKoguchiOrderID(NewKoguchi_orderidReq req, final NewKoguchiOrderIDCallback callback){
        apiService.NewKoguchiOrderID(req).enqueue(new Callback<NewKoguchi_orderIDResponse>() {
            @Override
            public void onResponse(Call<NewKoguchi_orderIDResponse> call, Response<NewKoguchi_orderIDResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<NewKoguchi_orderIDResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


// New Koguchi ShipCompany get

    public interface NewKoguchiShipCompanycall {
        void onSucess(int status, NKoguchiShipCompResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewKoguchiShipCompany(NKoguchi_ShipCompanyReq req, final NewKoguchiShipCompanycall callback) {
        apiService.NewKoguchiShipCompany(req).enqueue(new Callback<NKoguchiShipCompResult>() {
            @Override
            public void onResponse(Call<NKoguchiShipCompResult> call, Response<NKoguchiShipCompResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NKoguchiShipCompResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }

// New Koguchi Submit

    public interface NewKoguchiSubmitcall {
        void onSucess(int status, SubmitKoguchiResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  NewKoguchiSubmit(SubmitnewKoguchiReq req, final NewKoguchiSubmitcall callback) {
        apiService.NewKoguchiSubmit(req).enqueue(new Callback<SubmitKoguchiResult>() {
            @Override
            public void onResponse(Call<SubmitKoguchiResult> call, Response<SubmitKoguchiResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<SubmitKoguchiResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });

    }

    // New Koguchi Print

    public interface NewKoguchiPrintcall {
        void onSucess(int status, NewkoguchiPrintResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewKoguchiPrint(NewKoguchiPrintRequest req, final NewKoguchiPrintcall callback) {
        apiService.NewKoguchiPrint(req).enqueue(new Callback<NewkoguchiPrintResult>() {
            @Override
            public void onResponse(Call<NewkoguchiPrintResult> call, Response<NewkoguchiPrintResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }  else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NewkoguchiPrintResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });

    }

    // New Koguchi OrderId Scan
    public interface NewKoguchiOrderIDScancall {
        void onSucess(int status, OrderIdScanKoguchiResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewKoguchiOrderIDScan(OrderIdScanKoguchiReq req, final NewKoguchiOrderIDScancall callback) {
        apiService.NewKoguchiOrderIDScan(req).enqueue(new Callback<OrderIdScanKoguchiResponse>() {
            @Override
            public void onResponse(Call<OrderIdScanKoguchiResponse> call, Response<OrderIdScanKoguchiResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }
            @Override
            public void onFailure(Call<OrderIdScanKoguchiResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    // New Koguchi Submitbox size

    public interface SubmitKoguchiBoxsizecall {
        void onSucess(int status, SubmitkoguchiboxsizeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  SubmitKoguchiBoxsize(SubmitKoguchiBoxsizeReq req, final SubmitKoguchiBoxsizecall callback) {
        apiService.SubmitKoguchiBoxsize(req).enqueue(new Callback<SubmitkoguchiboxsizeResponse>() {
            @Override
            public void onResponse(Call<SubmitkoguchiboxsizeResponse> call, Response<SubmitkoguchiboxsizeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<SubmitkoguchiboxsizeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }

    //Arrival_ID_Activity
    //ArrivalId check

    public interface ArrivalIDcheckcall {
        void onSucess(int status, ArrivalIDCheckResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  ArrivalIDCheck(ArrivalIDCheckRequest req, final ArrivalIDcheckcall callback) {
        apiService.ArrivalIDCheck(req).enqueue(new Callback<ArrivalIDCheckResponse>() {
            @Override
            public void onResponse(Call<ArrivalIDCheckResponse> call, Response<ArrivalIDCheckResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ArrivalIDCheckResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Barcode check

    public interface BarcodeCheckcall {
        void onSucess(int status,
                      BarcodeCheckResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  BarcodeCheckAPI(BarcodeCheckRequest req, final BarcodeCheckcall callback) {
        apiService.BarcodeCheckAPI(req).enqueue(new Callback<BarcodeCheckResponse>() {
            @Override
            public void onResponse(Call<BarcodeCheckResponse> call, Response<BarcodeCheckResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    Log.e("FFFFFFFFFFF", e.toString());
                }
            }

            @Override
            public void onFailure(Call<BarcodeCheckResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Normal and MiddleArrival Submission

    public interface Arrivalsubmissioncall {
        void onSucess(int status, ArrivalsubmissionResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  ArrivalSubmissionAPI(ArrivalsubmissionRequest req, final Arrivalsubmissioncall callback) {
        apiService.ArrivalSubmissionAPI(req).enqueue(new Callback<ArrivalsubmissionResponse>() {
            @Override
            public void onResponse(Call<ArrivalsubmissionResponse> call, Response<ArrivalsubmissionResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ArrivalsubmissionResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();//
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //Direct Arrival Submission
    public interface DirectArrivalsubmissioncall {
        void onSucess(int status, ArrivalsubmissionResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  DirectArrivalSubmissionAPI(DirectArrivalSubmissionRequest req, final DirectArrivalsubmissioncall callback) {
        apiService.DirectArrivalSubmissionAPI(req).enqueue(new Callback<ArrivalsubmissionResponse>() {
            @Override
            public void onResponse(Call<ArrivalsubmissionResponse> call, Response<ArrivalsubmissionResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ArrivalsubmissionResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Clear Event

    public interface ClearEventcall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  ClearEventAPI(ClearEventSubmissionRequest req, final ClearEventcall callback) {
        apiService.ClearEventAPI(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Stock Classification List

    public interface StockClassificationcall {
        void onSucess(int status, StockClassificationResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  StockClassificationAPI(StockClassificationRequest req, final StockClassificationcall callback) {
        apiService.StockClassificationAPI(req).enqueue(new Callback<StockClassificationResponse>() {
            @Override
            public void onResponse(Call<StockClassificationResponse> call, Response<StockClassificationResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

             @Override
            public void onFailure(Call<StockClassificationResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //----------------------------------one_to_one_slipPrinter---------------------//
    //OneToOneGetCompany
    public interface OneToOneGetCompanycall {
        void onSucess(int status, NKoguchiShipCompResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  One_to_One_SlipPrintCompanies(NKoguchi_ShipCompanyReq req, final OneToOneGetCompanycall callback) {
        apiService.One_to_One_SlipPrintCompanies(req).enqueue(new Callback<NKoguchiShipCompResult>() {
            @Override
            public void onResponse(Call<NKoguchiShipCompResult> call, Response<NKoguchiShipCompResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    Log.e("EXCEPTION", e+"");
                }
            }

            @Override
            public void onFailure(Call<NKoguchiShipCompResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }

    //Update_Ship_OnetoOne
    public interface One_to_One_UpdateShipcall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  One_to_One_UpdateShip(UpdateShip_request req, final One_to_One_UpdateShipcall callback) {
        apiService.One_to_One_UpdateShip(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }

    //GetOrder ID
    public interface GetORderIDcall {
        void onSucess(int status, Daimaru_OnetoOne_OrderID_Res message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetORderID(Daimaru_OnetoOne_OrderIDReq req, final GetORderIDcall callback) {
        apiService.GetORderID(req).enqueue(new Callback<Daimaru_OnetoOne_OrderID_Res>() {
            @Override
            public void onResponse(Call<Daimaru_OnetoOne_OrderID_Res> call, Response<Daimaru_OnetoOne_OrderID_Res> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Daimaru_OnetoOne_OrderID_Res> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }

    //---------------------------------------------------------------//


    //-------------------Daimaru Shipping --------------------//

    //GetTotalNo. of Orders
    public interface DaimaruGetOrderNocallback {
        void onSucess(int status, Daimaru_GetOrderRespose message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruGetOrderNo(DaimaruShippingRequest req, final DaimaruGetOrderNocallback callback){

        apiService.DaimaruGetOrderNo(req).enqueue(new Callback<Daimaru_GetOrderRespose>()  {
            @Override
            public void onResponse(Call<Daimaru_GetOrderRespose> call, Response<Daimaru_GetOrderRespose> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }


            @Override
            public void onFailure(Call<Daimaru_GetOrderRespose> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });

    }


    //Daimasu CHECK ORDERID
    public interface DaimaruGetOrderCallback {
        void onSucess(int status, CheckOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruGetOrder(DaimaruShippingRequest req, final DaimaruGetOrderCallback callback){

        apiService.DaimaruGetOrder(req).enqueue(new Callback<CheckOrderResponse>()  {
            @Override
            public void onResponse(Call<CheckOrderResponse> call, Response<CheckOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Daimaru CHECK BARCODE
    public interface DaimaruCheckBarcodeCallback {
        void onSucess(int status, CheckBarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruCheckBarcode(DaimaruShippingRequest req, final DaimaruCheckBarcodeCallback callback){

        apiService.DaimaruCheckBarcode(req).enqueue(new Callback<CheckBarcodeResponse>()  {
            @Override
            public void onResponse(Call<CheckBarcodeResponse> call, Response<CheckBarcodeResponse> response) {
                int statusCode = response.code();
                Log.e(">>>>>>>>>>","<<<<<<<<<<<"+statusCode);
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckBarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Daimaru SEND QTY
    public interface DaimaruFixOrderCallback {
        void onSucess(int status, FixOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruFixOrder(DaimaruShippingRequest req, final DaimaruFixOrderCallback callback){

        apiService.DaimaruFixOrder(req).enqueue(new Callback<FixOrderResponse>()  {
            @Override
            public void onResponse(Call<FixOrderResponse> call, Response<FixOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FixOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //SEND BOX SIZe
    public interface DaimaruSetboxSizeCallback {
        void onSucess(int status, SetBoxSizeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruSetboxSize(DaimaruShippingRequest req, final DaimaruSetboxSizeCallback callback){

        apiService.DaimaruSetboxSize(req).enqueue(new Callback<SetBoxSizeResponse>()  {
            @Override
            public void onResponse(Call<SetBoxSizeResponse> call, Response<SetBoxSizeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SetBoxSizeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //CHECK ORDERID FOR SCAN PRODUCTS
    public interface DaimaruGetOrderNoProductCallback {
        void onSucess(int status, CheckOrderNoProductResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void DaimaruGetOrderNoProduct(NotScanProductShippingRequest req, final DaimaruGetOrderNoProductCallback callback){

        apiService.DaimaruGetOrderNoProduct(req).enqueue(new Callback<CheckOrderNoProductResponse>()  {
            @Override
            public void onResponse(Call<CheckOrderNoProductResponse> call, Response<CheckOrderNoProductResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckOrderNoProductResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //Update_Ship_Company
    public interface DaimaruUpdateShipcall {
        void onSucess(int status, ResponseBody message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  DaimaruUpdateKoguchi(DaimaruKoguchiShipUpdatereq req, final DaimaruUpdateShipcall callback) {
        apiService.DaimaruUpdateKoguchi(req).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //-----------------------------WWW6 Arrival Activity-----------------------//

    //GetArrivalOrderNo
    public interface GetArrivalOrderNocall {
        void onSucess(int status, GetArrivalOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetArrivalOrderNoAPI(GetArrivalOrderReq req, final GetArrivalOrderNocall callback) {
        apiService.GetArrivalOrderNoAPI(req).enqueue(new Callback<GetArrivalOrderResponse>() {
            @Override
            public void onResponse(Call<GetArrivalOrderResponse> call, Response<GetArrivalOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GetArrivalOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //GetArrivalBarcode

    public interface GetArrivalcall {
        void onSucess(int status, GetArrivalResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  GetArrivalAPI(GetArrivalReq req, final GetArrivalcall callback) {
        apiService.GetArrivalAPI(req).enqueue(new Callback<GetArrivalResponse>() {
            @Override
            public void onResponse(Call<GetArrivalResponse> call, Response<GetArrivalResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GetArrivalResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //GetArrivalBarcodeOrderNo

    public interface GetArrivalBarcodeOrderNocall {
        void onSucess(int status, GetArrivalBarcodeOrderNoResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  GetArrivalBarcodeOrderNoAPI(GetArrivalBarcodeOrderNoReq req, final GetArrivalBarcodeOrderNocall callback) {
        apiService.GetArrivalBarcodeOrderNoAPI(req).enqueue(new Callback<GetArrivalBarcodeOrderNoResponse>() {
            @Override
            public void onResponse(Call<GetArrivalBarcodeOrderNoResponse> call, Response<GetArrivalBarcodeOrderNoResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GetArrivalBarcodeOrderNoResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //AddArrivalBarcode

    public interface AddArrivalcall {
        void onSucess(int status, AddArrivalResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  AddArrivalAPI(AddArrivalReq req, final AddArrivalcall callback) {
        apiService.AddArrivalAPI(req).enqueue(new Callback<AddArrivalResponse>() {
            @Override
            public void onResponse(Call<AddArrivalResponse> call, Response<AddArrivalResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<AddArrivalResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    // --------------------------------BatchPickingW6--------------------------//

    //BatchListCall

    public interface BatchListcall {
        void onSucess(int status, BatchListResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  BatchListAPI(BatchListRequest req, final BatchListcall callback) {
        apiService.BatchListAPI(req).enqueue(new Callback<BatchListResponse>() {
            @Override
            public void onResponse(Call<BatchListResponse> call, Response<BatchListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BatchListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    // BoxBatch Request
    public interface GetboxBatchcall {
        void onSucess(int status, BoxBatchResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetboxBatch(BoxBatchRequest req, final GetboxBatchcall callback){
        apiService.GetboxBatch(req).enqueue(new Callback<BoxBatchResponse>() {
            @Override
            public void onResponse(Call<BoxBatchResponse> call, Response<BoxBatchResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BoxBatchResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    // BoxBatch submit request
    public interface BoxBatchSumbitcall {
        void onSucess(int status, BoxBatchSubmitResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  BoxBatchSubmit(BoxBatchSubmitReq req, final BoxBatchSumbitcall callback){
        apiService.BoxBatchSubmit(req).enqueue(new Callback<BoxBatchSubmitResponse>() {
            @Override
            public void onResponse(Call<BoxBatchSubmitResponse> call, Response<BoxBatchSubmitResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BoxBatchSubmitResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    // GetBatchPicking Request
    public interface GetBatchPickingcall {
        void onSucess(int status, BatchPickingResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetBatchPicking(BatchPickingRequest req, final GetBatchPickingcall callback){
        apiService.GetBatchPicking(req).enqueue(new Callback<BatchPickingResponse>() {
            @Override
            public void onResponse(Call<BatchPickingResponse> call, Response<BatchPickingResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());

                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BatchPickingResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    // FixBatchPicking Request
    public interface FixBatchPickingcall {
        void onSucess(int status, FixBatchPickingResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  FixBatchPicking(FixBatchPickingRequest req, final FixBatchPickingcall callback){
        apiService.FixBatchPicking(req).enqueue(new Callback<FixBatchPickingResponse>() {
            @Override
            public void onResponse(Call<FixBatchPickingResponse> call, Response<FixBatchPickingResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<FixBatchPickingResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //----------------------------ReturnStockLOTEXP--------------------------

    // GETLOTEXP


    public interface GETLOTEXPcall {
        void onSucess(int status, ReturnStokeLotExpResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GETLOTEXP(ReturnStokeLotExpReq req, final GETLOTEXPcall callback){
        apiService.GETLOTEXP(req).enqueue(new Callback<ReturnStokeLotExpResult>() {
            @Override
            public void onResponse(Call<ReturnStokeLotExpResult> call, Response<ReturnStokeLotExpResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ReturnStokeLotExpResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //Submit Return Stoke

    public interface SubmitRetunStokecall {
        void onSucess(int status, SubmitReturnStokeResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  SubmitRetunStoke(SubmitReturnStokeReq req, final SubmitRetunStokecall callback){
        apiService.SubmitRetunStoke(req).enqueue(new Callback<SubmitReturnStokeResult>() {
            @Override
            public void onResponse(Call<SubmitReturnStokeResult> call, Response<SubmitReturnStokeResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SubmitReturnStokeResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //-------------------------------------------SimpleShipping Activity----------------------------------//

    // GetSettings
    public interface GetSettingsAPIcall {
        void onSucess(int status, GetShippingSettingResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  GetSettingsAPI(GetShippingSettingsReq req, final GetSettingsAPIcall callback){
        apiService.GetSettingsAPI(req).enqueue(new Callback<GetShippingSettingResponse>() {
            @Override
            public void onResponse(Call<GetShippingSettingResponse> call, Response<GetShippingSettingResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<GetShippingSettingResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //CHECK ORDERID
    public interface CheckOrderCallback {
        void onSucess(int status, CheckShipOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void CheckOrderShipping(CheckOrderReq req, final CheckOrderCallback callback){

        apiService.CheckOrderShipping(req).enqueue(new Callback<CheckShipOrderResponse>()  {
            @Override
            public void onResponse(Call<CheckShipOrderResponse> call, Response<CheckShipOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckShipOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }



    //CHECK BARCODE
    public interface CheckBarcodeShipCallback {
        void onSucess(int status, CheckBarcodeShipResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void CheckBarcodeShipping(CheckBarcodeShipReq req, final CheckBarcodeShipCallback callback){

        apiService.CheckBarcodeShipping(req).enqueue(new Callback<CheckBarcodeShipResponse>()  {
            @Override
            public void onResponse(Call<CheckBarcodeShipResponse> call, Response<CheckBarcodeShipResponse> response) {
                int statusCode = response.code();
                Log.e(">>>>>>>>>>","<<<<<<<<<<<"+statusCode);
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckBarcodeShipResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //SEND QTY
    public interface PickBarcodeCallback {
        void onSucess(int status, PickBarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void PickBarcodeAPI(PickBarcodeReq req, final PickBarcodeCallback callback){

        apiService.PickBarcodeAPI(req).enqueue(new Callback<PickBarcodeResponse>()  {
            @Override
            public void onResponse(Call<PickBarcodeResponse> call, Response<PickBarcodeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<PickBarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }



    //DirectArrival Location
    //Stock Classification List

    public interface LocationListcall {
        void onSucess(int status, DirArrivalLoctionResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  LocationList(LocationReq req, final LocationListcall callback) {
        apiService.LocationList(req).enqueue(new Callback<DirArrivalLoctionResult>() {
            @Override
            public void onResponse(Call<DirArrivalLoctionResult> call, Response<DirArrivalLoctionResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<DirArrivalLoctionResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //------------------------------------------ShippingLoop orderid --------------------------------------//

    public interface LoopShipingOrdercall {
        void onSucess(int status, LoopOrderResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  LoopShipingOrderAPI(LoopOrderReq req, final LoopShipingOrdercall callback) {
        apiService.LoopShipingOrderAPI(req).enqueue(new Callback<LoopOrderResult>() {
            @Override
            public void onResponse(Call<LoopOrderResult> call, Response<LoopOrderResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<LoopOrderResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //ShippingLoop Barcode

    public interface LoopShipingBarcodeAPIcall {
        void onSucess(int status, LoopBarcodeResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  LoopShipingBarcodeAPI(LoopBarcodeReq req, final LoopShipingBarcodeAPIcall callback) {
        apiService.LoopShipingBarcodeAPI(req).enqueue(new Callback<LoopBarcodeResult>() {
            @Override
            public void onResponse(Call<LoopBarcodeResult> call, Response<LoopBarcodeResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<LoopBarcodeResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

//ShippingLoop SUbmit

    public interface SubmitLoopShipingAPIcall {
        void onSucess(int status, LoopSubmitResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  SubmitLoopShipingAPI(SubmitReqLoop req, final SubmitLoopShipingAPIcall callback) {
        apiService.SubmitLoopShipingAPI(req).enqueue(new Callback<LoopSubmitResult>() {
            @Override
            public void onResponse(Call<LoopSubmitResult> call, Response<LoopSubmitResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<LoopSubmitResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }
//GETLoopOrder SUbmit

    public interface GETLoopOrderAPIcall {
        void onSucess(int status, GetorderResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  GETLoopOrder(GetorderRequest req, final GETLoopOrderAPIcall callback) {
        apiService.GETLoopOrder(req).enqueue(new Callback<GetorderResult>() {
            @Override
            public void onResponse(Call<GetorderResult> call, Response<GetorderResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<GetorderResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

//GETLoopPendingOrder

    public interface GETLoopPendingOrdercall {
        void onSucess(int status, GetpendingorderResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  GETLoopPendingOrder(LoopOrderReq req, final GETLoopPendingOrdercall callback) {
        apiService.GETLoopPendingOrder(req).enqueue(new Callback<GetpendingorderResult>() {
            @Override
            public void onResponse(Call<GetpendingorderResult> call, Response<GetpendingorderResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());

                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetpendingorderResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    // BoxCashRegister  Activity

    //GetOrderID
    public interface GetOrderIDCallback {
        void onSucess(int status, BoxCashOrderIDResp message) throws JsonIOException;

        void onError(int status, ResponseBody error);

        void onFaliure();

        void onNetworkFailure();
    }

    public void GetBoxOrderID(BoxCashOrderIDRequest req, final GetOrderIDCallback callback) {
        apiService.GetBoxOrderID( req).enqueue(new Callback<BoxCashOrderIDResp>() {
            @Override
            public void onResponse(Call<BoxCashOrderIDResp> call, Response<BoxCashOrderIDResp> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<BoxCashOrderIDResp> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //GetOrderID
    public interface GetBoxListCallback {
        void onSucess(int status, BoxListResp message) throws JsonIOException;

        void onError(int status, ResponseBody error);

        void onFaliure();

        void onNetworkFailure();
    }

    public void GetBoxList(BoxListRequest req, final GetBoxListCallback callback) {
        apiService.GetBoxList(req).enqueue(new Callback<BoxListResp>() {
            @Override
            public void onResponse(Call<BoxListResp> call, Response<BoxListResp> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BoxListResp> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //PickRequest
    public interface SetPickProductCallback {
        void onSucess(int status, PickProductRes message) throws JsonIOException;

        void onError(int status, ResponseBody error);

        void onFaliure();

        void onNetworkFailure();
    }

    public void SetPickProduct(PickProductReq req, final SetPickProductCallback callback) {
        apiService.SetPickProduct( req).enqueue(new Callback<PickProductRes>() {
            @Override
            public void onResponse(Call<PickProductRes> call, Response<PickProductRes> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<PickProductRes> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //GetOrderID
    public interface GetBoxNoCallback {
        void onSucess(int status, GetBoxNoResponse message) throws JsonIOException;

        void onError(int status, ResponseBody error);

        void onFaliure();

        void onNetworkFailure();
    }

    public void GetBoxNo(NextBoxReq req, final GetBoxNoCallback callback) {
        apiService.GetBoxNo(req).enqueue(new Callback<GetBoxNoResponse>() {
            @Override
            public void onResponse(Call<GetBoxNoResponse> call, Response<GetBoxNoResponse> response) {
                int statusCode = response.code();
                try {
                    Log.e("status codee     ", ">>>>>>>>>>>>>>>>>>>>>>>>>>>   " + statusCode);
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }

                } catch (Exception e) {
                    Log.e("Exception   ", "   " + e);
                }
            }

            @Override
            public void onFailure(Call<GetBoxNoResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Get PACKINGList
    public interface GetPackingListcallback {
        void onSucess(int status, GetPackingListResponse message) throws JsonIOException;

        void onError(int status, ResponseBody error);

        void onFaliure();

        void onNetworkFailure();
    }

    public void GetPackingListAPI(BoxCashOrderIDRequest req, final GetPackingListcallback callback) {
        apiService.GetPackingListAPI(req).enqueue(new Callback<GetPackingListResponse>() {
            @Override
            public void onResponse(Call<GetPackingListResponse> call, Response<GetPackingListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetPackingListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Get Box Recreate
    public interface GetBoxRecreatecallback {
        void onSucess(int status, BoxRecreateResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void GetBoxRecreateAPI(BoxRecreateReq req, final GetBoxRecreatecallback callback) {
        apiService.GetBoxRecreateAPI(req).enqueue(new Callback<BoxRecreateResponse>() {
            @Override
            public void onResponse(Call<BoxRecreateResponse> call, Response<BoxRecreateResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BoxRecreateResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //Getproduct list click
    public interface boxproductlistclickcallback {
        void onSucess(int status, BoxProductclickListResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void boxproductlistclick(BoxProductClickRequest req, final boxproductlistclickcallback callback) {
        apiService.boxproductlistclick(req).enqueue(new Callback<BoxProductclickListResponse>() {
            @Override
            public void onResponse(Call<BoxProductclickListResponse> call, Response<BoxProductclickListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BoxProductclickListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //--------------------------CustomerID Arrival Screen------------------//

    //Check Customer ID
    public interface CheckCustomercallback {
        void onSucess(int status, CustomerCheckResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void CheckCustomerID(CustomerCheckReq req, final CheckCustomercallback callback) {
        apiService.CheckCustomerID(req).enqueue(new Callback<CustomerCheckResponse>() {
            @Override
            public void onResponse(Call<CustomerCheckResponse> call, Response<CustomerCheckResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CustomerCheckResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Check Barcode
    public interface CheckCustomerBarcodecallback {
        void onSucess(int status, CustomerBarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void CheckBarcodeArrival(CustomerBarcodeReq req, final CheckCustomerBarcodecallback callback) {
        apiService.CheckBarcodeArrival(req).enqueue(new Callback<CustomerBarcodeResponse>() {
            @Override
            public void onResponse(Call<CustomerBarcodeResponse> call, Response<CustomerBarcodeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CustomerBarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //Submit Arrival
    public interface submitArrivalcallback {
        void onSucess(int status, SubmitArrivalResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void SubmitArrival(SubmitArrivalReq req, final submitArrivalcallback callback) {
        apiService.SubmitArrival(req).enqueue(new Callback<SubmitArrivalResponse>() {
            @Override
            public void onResponse(Call<SubmitArrivalResponse> call, Response<SubmitArrivalResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SubmitArrivalResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }

    //--------------------------New MoveStock Screen------------------//

    //Check Location
    public interface CheckLocationcallback {
        void onSucess(int status, LocationCheckResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void LocationCheck(LocationCheckReq req, final CheckLocationcallback callback) {
        apiService.LocationCheck(req).enqueue(new Callback<LocationCheckResponse>() {
            @Override
            public void onResponse(Call<LocationCheckResponse> call, Response<LocationCheckResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LocationCheckResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //Check Barcode
    public interface CheckBarcodeStockcallback {
        void onSucess(int status, BarcodeResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void BarcodeCheck(BarcodeCheckReq req, final CheckBarcodeStockcallback callback) {
        apiService.BarcodeCheck(req).enqueue(new Callback<BarcodeResponse>() {
            @Override
            public void onResponse(Call<BarcodeResponse> call, Response<BarcodeResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BarcodeResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //Submit Stock
    public interface SubmitStockcallback {
        void onSucess(int status, SubmitStockResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void SubmitStockAPI(SubmitStockReq req, final SubmitStockcallback callback) {
        apiService.SubmitStockAPI(req).enqueue(new Callback<SubmitStockResponse>() {
            @Override
            public void onResponse(Call<SubmitStockResponse> call, Response<SubmitStockResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SubmitStockResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    //------------------------------Invoice Shipping Screen---------------------------//

    //ORDERID API CALL
    public interface OrderIDCheckCallback {
        void onSucess(int status, CheckInvoiceShipOrderResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void OrderIDCheck(com.itechvision.ecrobo.pickman.Models.InvoiceShipping.CheckInvoiceOrder.CheckOrderReq req, final OrderIDCheckCallback callback){

        apiService.OrderIDCheck(req).enqueue(new Callback<CheckInvoiceShipOrderResponse>()  {
            @Override
            public void onResponse(Call<CheckInvoiceShipOrderResponse> call, Response<CheckInvoiceShipOrderResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<CheckInvoiceShipOrderResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    //TRACKING API CALL
    public interface TrackSubmitCallback {
        void onSucess(int status, SubmitMediaResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void TrackSubmitAPI(SubmitMediaReq req, final TrackSubmitCallback callback){

        apiService.TrackSubmitAPI(req).enqueue(new Callback<SubmitMediaResponse>()  {
            @Override
            public void onResponse(Call<SubmitMediaResponse> call, Response<SubmitMediaResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<SubmitMediaResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    // --------------------------------DMBatchPicking--------------------------//

    //BatchListCall

    public interface DmBatchListcall {
        void onSucess(int status, DmBatchListResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  DmBatchListAPI(DmBatchListRequest req, final DmBatchListcall callback) {
        apiService.DmBatchListAPI(req).enqueue(new Callback<DmBatchListResponse>() {
            @Override
            public void onResponse(Call<DmBatchListResponse> call, Response<DmBatchListResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<DmBatchListResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });
    }


    // BoxBatch Request
    public interface DmGetboxBatchcall {
        void onSucess(int status, DmBoxBatchResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  DmGetboxBatch(DmBoxBatchRequest req, final DmGetboxBatchcall callback){
        apiService.DmGetboxBatch(req).enqueue(new Callback<DmBoxBatchResponse>() {
            @Override
            public void onResponse(Call<DmBoxBatchResponse> call, Response<DmBoxBatchResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DmBoxBatchResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    // BoxBatch submit request
    public interface DmBoxBatchSumbitcall {
        void onSucess(int status, DmBoxBatchSubmitResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  DmBoxBatchSubmit(DmBoxBatchSubmitReq req, final DmBoxBatchSumbitcall callback){
        apiService.DmBoxBatchSubmit(req).enqueue(new Callback<DmBoxBatchSubmitResponse>() {
            @Override
            public void onResponse(Call<DmBoxBatchSubmitResponse> call, Response<DmBoxBatchSubmitResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DmBoxBatchSubmitResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }


    // GetBatchPicking Request
    public interface DmGetBatchPickingcall {
        void onSucess(int status, DmBatchPickingResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  DmGetBatchPicking(DmBatchPickingRequest req, final DmGetBatchPickingcall callback){
        apiService.DmGetBatchPicking(req).enqueue(new Callback<DmBatchPickingResponse>() {
            @Override
            public void onResponse(Call<DmBatchPickingResponse> call, Response<DmBatchPickingResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());

                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DmBatchPickingResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    // FixBatchPicking Request
    public interface DmFixBatchPickingcall {
        void onSucess(int status, DmFixBatchPickingResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  DmFixBatchPicking(DmFixBatchPickingRequest req, final DmFixBatchPickingcall callback){
        apiService.DmFixBatchPicking(req).enqueue(new Callback<DmFixBatchPickingResponse>() {
            @Override
            public void onResponse(Call<DmFixBatchPickingResponse> call, Response<DmFixBatchPickingResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else if (statusCode == 404) {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<DmFixBatchPickingResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

    //-------------------------------------Invoice Print Activity ------------------------//


    // Invoice OrderID Scan

    public interface InvoiceOrderIDCallback {
        void onSucess(int status, Invoice_orderIDResponse message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  InvoiceOrderID(Invoice_orderidReq req, final InvoiceOrderIDCallback callback){
        apiService.InvoiceOrderID(req).enqueue(new Callback<Invoice_orderIDResponse>() {
            @Override
            public void onResponse(Call<Invoice_orderIDResponse> call, Response<Invoice_orderIDResponse> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    }
                    else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }else {
                        callback.onError(statusCode, response.errorBody());
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Invoice_orderIDResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                }else{
                    callback.onFaliure();
                }
            }
        });
    }

/*
// New Koguchi ShipCompany get

    public interface NewKoguchiShipCompanycall {
        void onSucess(int status, NKoguchiShipCompResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  NewKoguchiShipCompany(NKoguchi_ShipCompanyReq req, final NewKoguchiShipCompanycall callback) {
        apiService.NewKoguchiShipCompany(req).enqueue(new Callback<NKoguchiShipCompResult>() {
            @Override
            public void onResponse(Call<NKoguchiShipCompResult> call, Response<NKoguchiShipCompResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NKoguchiShipCompResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }*/

// Invoice Submit

    public interface InvoiceSubmitcall {
        void onSucess(int status, SubmitInvoiceResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }

    public void  InvoiceSubmit(SubmitInvoiceReq req, final InvoiceSubmitcall callback) {
        apiService.InvoiceSubmit(req).enqueue(new Callback<SubmitInvoiceResult>() {
            @Override
            public void onResponse(Call<SubmitInvoiceResult> call, Response<SubmitInvoiceResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<SubmitInvoiceResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });

    }

    // New Invoice Print

    public interface NewInvoicePrintcall {
        void onSucess(int status, InvoicePrintResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  InvoicePrint(InvoicePrintRequest req, final NewInvoicePrintcall callback) {
        apiService.InvoicePrint(req).enqueue(new Callback<InvoicePrintResult>() {
            @Override
            public void onResponse(Call<InvoicePrintResult> call, Response<InvoicePrintResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    }  else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<InvoicePrintResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });

    }

    // Invoice ShipCompany get

    public interface InvoiceShipCompanycall {
        void onSucess(int status, InvoiceShipCompResult message) throws JsonIOException;
        void onError(int status, ResponseBody error);
        void onFaliure();
        void onNetworkFailure();
    }
    public void  InvoiceShipCompany(Invoice_ShipCompanyReq req, final InvoiceShipCompanycall callback) {
        apiService.InvoiceShipCompany(req).enqueue(new Callback<InvoiceShipCompResult>() {
            @Override
            public void onResponse(Call<InvoiceShipCompResult> call, Response<InvoiceShipCompResult> response) {
                int statusCode = response.code();
                try {
                    if (statusCode == 200) {
                        callback.onSucess(statusCode, response.body());
                    } else if (statusCode == 422) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 401) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 402) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 406) {
                        callback.onError(statusCode, response.errorBody());
                    } else if (statusCode == 403) {
                        callback.onError(statusCode, response.errorBody());
                    } else {
                        callback.onError(statusCode, response.errorBody());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<InvoiceShipCompResult> call, Throwable t) {
                if (t instanceof IOException) {
                    // Network error
                    callback.onNetworkFailure();
                } else {
                    callback.onFaliure();
                }
            }
        });


    }


    public DataManager() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }




}
