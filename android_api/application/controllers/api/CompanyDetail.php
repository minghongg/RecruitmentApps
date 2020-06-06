<?php
	use Restserver\Libraries\REST_Controller;

	defined('BASEPATH') OR exit('No direct script access allowed');
	
	require APPPATH . 'libraries/REST_Controller.php';
	require APPPATH . 'libraries/Format.php';

	class CompanyDetail extends REST_Controller{
		public function __construct()
		{
			parent::__construct();
			$this->load->model('Perusahaan_model','perusahaan');
		}
		public function index_get(){
			$id = $this->get('PerusahaanID');
			$company = $this->perusahaan->detailPerusahaan($id);
			if($company){
				$this->response([
					'status' => 200,
					'message' => '',
					'data' => $company
                ], REST_Controller::HTTP_OK);
			}
			else{
				$this->response([
					'status' => 200,
					'message' => '',
					'data' => null

					//$company
                ], REST_Controller::HTTP_OK);
			}
		}
	}
?>
